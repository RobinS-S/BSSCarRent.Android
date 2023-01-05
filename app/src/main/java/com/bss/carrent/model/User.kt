package com.bss.carrent.model

data class User(
    val firstName: String,
    val infix: String?,
    val lastName: String,
    val phoneInternationalCode: String,
    val phoneNumber: String
)