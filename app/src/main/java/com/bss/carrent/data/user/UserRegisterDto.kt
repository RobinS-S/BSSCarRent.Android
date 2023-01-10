package com.bss.carrent.data.user

import java.time.LocalDate

data class UserRegisterDto(
    val email: String,
    val password: String,
    val firstName: String,
    val infix: String?,
    val lastName: String,
    val phoneInternationalCode: String,
    val phoneNumber: String,
    val birthDate: LocalDate
) : java.io.Serializable
