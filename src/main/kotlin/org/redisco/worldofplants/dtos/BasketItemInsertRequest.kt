package org.redisco.worldofplants.dtos

data class BasketItemInsertRequest(
    val plantName: String,
    val quantity: Int
)