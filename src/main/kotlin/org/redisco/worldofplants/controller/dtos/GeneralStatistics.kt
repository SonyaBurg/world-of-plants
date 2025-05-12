package org.redisco.worldofplants.controller.dtos

data class GeneralStatistics(
    val totalOrders: Long,
    val totalRevenue: Int,
    val totalUsers: Long,
    val totalPlantSold: Int
)