package org.redisco.worldofplants.controller.dtos

data class OrderStatusStatistics(
    val pendingOrdersCount: Int,
    val paidOrdersCount: Int,
    val shippedOrdersCount: Int,
    val completedOrdersCount: Int,
)