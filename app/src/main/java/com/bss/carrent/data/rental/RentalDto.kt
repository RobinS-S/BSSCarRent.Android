package com.bss.carrent.data.rental

import java.time.LocalDateTime

data class RentalDto(
    val id: Long,
    val reservedFrom: LocalDateTime,
    val reservedUntil: LocalDateTime,
    val deliveredAt: LocalDateTime?,
    val pickedUpAt: LocalDateTime?,
    val mileageTotal: Long,
    val drivingStyleScore: Double?,
    val kmPackage: Long,
    val tenantId: Long,
    val ownerId: Long,
    val carId: Long,
    val cancelled: Boolean
) : java.io.Serializable
