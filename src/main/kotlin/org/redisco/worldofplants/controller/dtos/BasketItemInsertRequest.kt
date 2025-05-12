package org.redisco.worldofplants.controller.dtos

data class BasketItemInsertRequest(
    val plantName: String,
    val quantity: Int
)