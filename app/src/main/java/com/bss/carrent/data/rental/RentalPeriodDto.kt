package com.bss.carrent.data.rental

import java.time.LocalDateTime

data class RentalPeriodDto(
    val reservedFrom: LocalDateTime,
    val reservedUntil: LocalDateTime,
    val carId: Long,
    val isCancelled: Boolean
) : java.io.Serializable
