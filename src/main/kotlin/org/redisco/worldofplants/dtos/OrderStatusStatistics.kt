package org.redisco.worldofplants.dtos

data class OrderStatusStatistics(
    val pendingOrdersCount: Int,
    val paidOrdersCount: Int,
    val shippedOrdersCount: Int,
    val completedOrdersCount: Int,
)