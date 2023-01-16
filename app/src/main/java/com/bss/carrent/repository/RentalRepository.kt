package com.bss.carrent.repository

import android.content.Context
import com.bss.carrent.api.RentalApiService
import com.bss.carrent.api.client.ApiClient
import com.bss.carrent.data.InvoiceDto
import com.bss.carrent.data.rental.RentalCreateDto
import com.bss.carrent.data.rental.RentalDeliverDto
import com.bss.carrent.data.rental.RentalDto
import com.bss.carrent.data.rental.RentalPeriodDto
import com.bss.carrent.misc.PrefsHelper
import java.io.IOException

class RentalRepository {
    suspend fun getForCarId(context: Context, id: Long): List<RentalDto>? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val rentals =
                    rentalApiService.getCarRentals(prefsHelper.getAuthorizationHeader()!!, id)
                if (rentals.code() == 200) {
                    rentals.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun getPeriodsForCarId(context: Context, id: Long): List<RentalPeriodDto>? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val periods =
                    rentalApiService.getCarRentalPeriods(prefsHelper.getAuthorizationHeader()!!, id)
                if (periods.code() == 200) {
                    periods.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun getMine(context: Context): List<RentalDto>? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val rentals = rentalApiService.getMyRentals(prefsHelper.getAuthorizationHeader()!!)
                if (rentals.code() == 200) {
                    rentals.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun getOwned(context: Context): List<RentalDto>? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val rentals =
                    rentalApiService.getOwnedRentals(prefsHelper.getAuthorizationHeader()!!)
                if (rentals.code() == 200) {
                    rentals.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun createRental(context: Context, rentalCreateDto: RentalCreateDto): RentalDto? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val rental = rentalApiService.createRental(
                    prefsHelper.getAuthorizationHeader()!!,
                    rentalCreateDto
                )
                if (rental.code() == 200) {
                    rental.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun markRentalAsPickedUp(context: Context, id: Long): RentalDto? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val rental =
                    rentalApiService.markRentalAsPickedUp(prefsHelper.getAuthorizationHeader()!!, id)
                if (rental.code() == 200) {
                    rental.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun markRentalAsDelivered(context: Context, id: Long, dto: RentalDeliverDto): InvoiceDto? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val invoice = rentalApiService.markRentalAsDelivered(
                    prefsHelper.getAuthorizationHeader()!!,
                    id,
                    dto
                )
                if (invoice.code() == 200) {
                    invoice.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun getCurrentRental(context: Context): RentalDto? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val rental =
                    rentalApiService.getCurrentRental(prefsHelper.getAuthorizationHeader()!!)
                if (rental.code() == 200) {
                    rental.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun deleteCurrentRental(context: Context): RentalDto? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val rental =
                    rentalApiService.deleteCurrentRental(prefsHelper.getAuthorizationHeader()!!)
                if (rental.code() == 200) {
                    rental.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }
}
