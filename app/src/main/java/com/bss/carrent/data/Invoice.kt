package com.bss.carrent.data

data class Invoice(
    val id: Long,
    val initialCost: Double,
    val mileageTotal: Double,
    val mileageCosts: Double,
    val kmPackage: Int,
    val totalPrice: Double,
    val totalHoursUsed: Double,
    val totalHourPrice: Double,
    val isPaid: Boolean,
    val renterId: Int,
    val ownerId: Int,
    val rentalId: Int

) : java.io.Serializable
