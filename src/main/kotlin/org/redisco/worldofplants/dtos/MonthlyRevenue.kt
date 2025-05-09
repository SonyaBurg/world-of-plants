package org.redisco.worldofplants.dtos

data class MonthlyRevenue(
    val labels: List<String>,
    val data: List<Double>
)