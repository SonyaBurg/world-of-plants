package org.redisco.worldofplants.service

import org.redisco.worldofplants.dtos.GeneralStatistics
import org.redisco.worldofplants.dtos.MonthlyRevenue
import org.redisco.worldofplants.dtos.Order
import org.redisco.worldofplants.dtos.OrderStatusStatistics
import org.redisco.worldofplants.entities.OrderEntity
import org.redisco.worldofplants.entities.OrderStatus
import org.redisco.worldofplants.repository.OrdersRepository
import org.redisco.worldofplants.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

interface OrdersService {
    fun getAllOrders(): List<Order>
    fun getOrdersByStatus(status: OrderStatus): List<Order>
    fun getOrdersForCustomer(customerEmail: String): List<Order>
    fun placeOrder(username: String, address: String)
    fun updateOrderStatus(orderNumber: Int, status: OrderStatus)
    fun getOrderDetails(orderNumber: Int): Order
    fun countOrdersByStatus(): OrderStatusStatistics
    fun getMonthlyRevenue(): MonthlyRevenue
    fun getGeneralStatistics(): GeneralStatistics
}

@Service
class OrdersServiceImpl(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val ordersRepository: OrdersRepository
) : OrdersService {
    @Transactional(readOnly = true)
    override fun getAllOrders(): List<Order> = ordersRepository.findAll().map { it.compose() }

    @Transactional(readOnly = true)
    override fun getOrdersByStatus(status: OrderStatus): List<Order> {
        return ordersRepository.findOrderEntitiesByStatus(status).map { it.compose() }.sortedByDescending { it.date }
    }

    @Transactional(readOnly = true)
    override fun getOrdersForCustomer(email: String): List<Order> {
        val user = userRepository.findUserEntityByLogin(email)
            ?: error("Customer with email $email not found")
        return user.orders.map { it.compose() }
    }

    @Transactional
    override fun placeOrder(username: String, address: String) {
        val user = userRepository.findByLogin(username)
            ?: error("User not found with login: $username")
        val basket = userService.getBasket(username)
        val orderEntity = OrderEntity(
            user = user,
            totalPrice = basket.totalPrice,
            address = address,
            date = LocalDateTime.now(),
            status = OrderStatus.PENDING,
        )
        orderEntity.orderedPlants.addAll(user.basketItems.map { it.toOrderedItemEntity(orderEntity) })
        ordersRepository.save(orderEntity)

        user.basketItems.clear()
        userRepository.save(user)
    }

    @Transactional
    override fun updateOrderStatus(orderNumber: Int, status: OrderStatus) {
        val order = ordersRepository.findOrderEntityByOrderNumber(orderNumber)
            ?: error("Order not found with ID: $orderNumber")

        order.status = status
        ordersRepository.save(order)
    }

    @Transactional(readOnly = true)
    override fun getOrderDetails(orderNumber: Int): Order {
        val order = ordersRepository.findOrderEntityByOrderNumber(orderNumber)
            ?: error("Order not found with ID: $orderNumber")
        return order.compose()
    }

    @Transactional(readOnly = true)
    override fun countOrdersByStatus(): OrderStatusStatistics {
        return OrderStatus.entries.map {
            ordersRepository.countAllByStatus(it)
        }.toOrderStatusStatistics()
    }

    @Transactional(readOnly = true)
    override fun getMonthlyRevenue(): MonthlyRevenue {
        val monthlyData = ordersRepository.findAggregatedRevenue()

        val months = mutableListOf<String>()
        val revenue = mutableListOf<Double>()

        for (row in monthlyData) {
            val year = row[0] as Int
            val month = row[1] as Int
            val total = row[2] as Double

            val monthName = Month.of(month).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
            months.add("$monthName $year")
            revenue.add(total / 100)
        }
        return MonthlyRevenue(months, revenue)
    }

    @Transactional(readOnly = true)
    override fun getGeneralStatistics(): GeneralStatistics {
        val totalOrders = ordersRepository.count()
        val totalRevenue = ordersRepository.findTotalRevenue()
        val totalUsers = userRepository.count()
        val totalPlantSold = ordersRepository.findTotalPlantsSold()
        return GeneralStatistics(
            totalRevenue = totalRevenue / 100,
            totalUsers = totalUsers,
            totalOrders = totalOrders,
            totalPlantSold = totalPlantSold
        )
    }
}