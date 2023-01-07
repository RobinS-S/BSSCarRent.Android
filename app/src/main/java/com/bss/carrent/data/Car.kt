package com.bss.carrent.data

import java.time.LocalDate

data class Car(
    val id: Long,
    val ownerId: Long,
    val brand: String,
    val model: String,
    val color: String,
    val kilometersCurrent: Long,
    val pricePerHour: Double,
    val pricePerKilometer: Double,
    val licensePlate: String,
    val constructed: LocalDate,
    val apkUntil: LocalDate,
    val carType: CarType,
    val fuelType: CombustionFuelType?,
    val initialCost: Double,
    val lat: Double,
    val lng: Double,
    val imageIds: List<Long>
) : java.io.Serializable
