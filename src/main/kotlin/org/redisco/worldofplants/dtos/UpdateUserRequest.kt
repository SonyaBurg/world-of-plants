package org.redisco.worldofplants.dtos

data class UpdateUserRequest(
    val login: String,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?,
    val address: String?
)