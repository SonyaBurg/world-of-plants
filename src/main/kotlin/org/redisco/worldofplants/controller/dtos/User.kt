package org.redisco.worldofplants.controller.dtos

data class User(
    val login: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val address: String
)