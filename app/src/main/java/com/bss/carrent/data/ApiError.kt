package com.bss.carrent.data

import java.time.LocalDateTime

data class ApiError(
    val timestamp: LocalDateTime?,
    val error: String?
)
