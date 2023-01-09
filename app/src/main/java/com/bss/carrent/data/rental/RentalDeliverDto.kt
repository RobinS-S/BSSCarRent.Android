package com.bss.carrent.data.rental

data class RentalDeliverDto(
    val mileageTotal: Long,
    val drivingStyleScore: Double,
    val lat: Double,
    val lng: Double
) : java.io.Serializable
