package com.bss.carrent.data.user

data class UserDto(
    val id: Long,
    val firstName: String,
    val infix: String?,
    val lastName: String,
    val phoneInternationalCode: String,
    val phoneNumber: String
) : java.io.Serializable
