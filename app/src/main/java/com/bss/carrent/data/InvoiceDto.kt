package com.bss.carrent.data

data class InvoiceDto(
    val id: Long,
    val initialCost: Double,
    val mileageTotal: Double,
    val mileageCosts: Double,
    val kmPackage: Long,
    val totalPrice: Double,
    val totalHoursUsed: Double,
    val totalHourPrice: Double,
    val isPaid: Boolean,
    val renterId: Long,
    val ownerId: Long,
    val rentalId: Long
) : java.io.Serializable
