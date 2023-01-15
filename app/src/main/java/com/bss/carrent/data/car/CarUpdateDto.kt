package com.bss.carrent.data.car

import java.time.LocalDate

data class CarUpdateDto(
    var brand: String,
    var model: String,
    var color: String,
    var kilometersCurrent: Long,
    var pricePerKilometer: Double,
    var pricePerHour: Double,
    var licensePlate: String,
    var apkUntil: LocalDate,
    var initialCost: Double
)
