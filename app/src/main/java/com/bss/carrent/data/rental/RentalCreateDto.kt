package com.bss.carrent.data.rental

import java.time.LocalDateTime

data class RentalCreateDto(
    val reservedFrom: LocalDateTime,
    val reservedUntil: LocalDateTime,
    val kmPackage: Long,
    val carId: Long
) : java.io.Serializable
