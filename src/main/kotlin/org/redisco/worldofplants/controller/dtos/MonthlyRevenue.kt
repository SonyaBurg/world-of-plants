package org.redisco.worldofplants.controller.dtos

data class MonthlyRevenue(
    val labels: List<String>,
    val data: List<Double>
)