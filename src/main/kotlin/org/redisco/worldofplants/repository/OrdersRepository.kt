package org.redisco.worldofplants.repository

import org.redisco.worldofplants.entities.OrderEntity
import org.redisco.worldofplants.entities.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface OrdersRepository : JpaRepository<OrderEntity, UUID> {
    @Query("SELECT o FROM OrderEntity o WHERE o.status = :status")
    fun findOrderEntitiesByStatus(status: OrderStatus): List<OrderEntity>

    @Query("SELECT o FROM OrderEntity o WHERE o.orderNumber = :orderNumber")
    fun findOrderEntityByOrderNumber(orderNumber: Int): OrderEntity?

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.status = :status")
    fun countAllByStatus(status: OrderStatus): Int

    @Query(
        """
    SELECT EXTRACT(YEAR FROM o.date) as year, EXTRACT(MONTH FROM o.date) as month, SUM(o.totalPrice) as revenue
    FROM OrderEntity o 
    GROUP BY EXTRACT(YEAR FROM o.date), EXTRACT(MONTH FROM o.date) 
    ORDER BY year, month
              """
    )
    fun findAggregatedRevenue(): List<Array<Any>>

    @Query("SELECT SUM(o.totalPrice) FROM OrderEntity o")
    fun findTotalRevenue(): Int

    @Query("SELECT SUM(o.quantity) FROM OrderedPlantEntity o")
    fun findTotalPlantsSold(): Int
}