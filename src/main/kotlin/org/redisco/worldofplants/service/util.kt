package org.redisco.worldofplants.service

import org.redisco.worldofplants.controller.dtos.BasketItem
import org.redisco.worldofplants.controller.dtos.Order
import org.redisco.worldofplants.controller.dtos.OrderItem
import org.redisco.worldofplants.controller.dtos.OrderStatusStatistics
import org.redisco.worldofplants.controller.dtos.Plant
import org.redisco.worldofplants.controller.dtos.User
import org.redisco.worldofplants.data.entities.BasketItemEntity
import org.redisco.worldofplants.data.entities.OrderEntity
import org.redisco.worldofplants.data.entities.OrderedPlantEntity
import org.redisco.worldofplants.data.entities.PlantEntity
import org.redisco.worldofplants.data.entities.UserEntity
import java.time.format.DateTimeFormatter

fun OrderEntity.compose(): Order {
    val items = orderedPlants.map { it.compose() }
    return Order(
        address = address,
        date = date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
        items = items,
        status = status,
        orderNumber = orderNumber,
        user = user.compose(),
        totalPrice = totalPrice / 100
    )
}

fun OrderedPlantEntity.compose(): OrderItem = OrderItem(
    quantity = quantity,
    plant = Plant(name, category, price, description, pictureLink)
)

fun BasketItemEntity.compose(): BasketItem =
    BasketItem(
        quantity = quantity,
        plant = plant.compose()
    )

fun PlantEntity.compose(): Plant =
    Plant(
        name = name,
        category = category,
        price = price,
        description = description,
        pictureLink = pictureLink
    )

fun BasketItemEntity.toOrderedItemEntity(orderEntity: OrderEntity): OrderedPlantEntity = OrderedPlantEntity(
    quantity = quantity,
    name = plant.name,
    order = orderEntity,
    category = plant.category,
    price = plant.price,
    description = plant.description,
    pictureLink = plant.pictureLink
)

fun UserEntity.compose() = User(
    login = login,
    email = email,
    phoneNumber = phoneNumber ?: "",
    firstName = firstName ?: "",
    lastName = lastName ?: "",
    address = address ?: "",
)

fun List<Int>.toOrderStatusStatistics() = OrderStatusStatistics(
    pendingOrdersCount = get(0),
    paidOrdersCount = get(1),
    shippedOrdersCount = get(2),
    completedOrdersCount = get(3)
)