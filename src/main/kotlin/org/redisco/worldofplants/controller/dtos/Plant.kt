package org.redisco.worldofplants.controller.dtos

data class Plant(
    val name: String,
    val category: String,
    val price: Int,
    val description: String,
    val pictureLink: String
)