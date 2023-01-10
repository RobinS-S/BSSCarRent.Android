package com.bss.carrent.repository

import android.content.Context
import com.bss.carrent.api.RentalApiService
import com.bss.carrent.api.client.ApiClient
import com.bss.carrent.data.InvoiceDto
import com.bss.carrent.data.rental.RentalCreateDto
import com.bss.carrent.data.rental.RentalDto
import com.bss.carrent.data.rental.RentalPeriodDto
import com.bss.carrent.misc.AuthHelper
import java.io.IOException

class RentalRepository {
    suspend fun getForCarId(context: Context, id: Long): List<RentalDto>? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val rentals =
                    rentalApiService.getCarRentals(authHelper.getAuthorizationHeader()!!, id)
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
            ApiClient.createService(RentalApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val periods =
                    rentalApiService.getCarRentalPeriods(authHelper.getAuthorizationHeader()!!, id)
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
            ApiClient.createService(RentalApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val rentals = rentalApiService.getMyRentals(authHelper.getAuthorizationHeader()!!)
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
            ApiClient.createService(RentalApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val rentals =
                    rentalApiService.getOwnedRentals(authHelper.getAuthorizationHeader()!!)
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
            ApiClient.createService(RentalApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val rental = rentalApiService.createRental(
                    authHelper.getAuthorizationHeader()!!,
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
            ApiClient.createService(RentalApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val rental =
                    rentalApiService.markRentalAsPickedUp(authHelper.getAuthorizationHeader()!!, id)
                if (rental.code() == 200) {
                    rental.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun markRentalAsDelivered(context: Context, id: Long): InvoiceDto? {
        val rentalApiService =
            ApiClient.createService(RentalApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val invoice = rentalApiService.markRentalAsDelivered(
                    authHelper.getAuthorizationHeader()!!,
                    id
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
            ApiClient.createService(RentalApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val rental =
                    rentalApiService.getCurrentRental(authHelper.getAuthorizationHeader()!!)
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
            ApiClient.createService(RentalApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val rental =
                    rentalApiService.deleteCurrentRental(authHelper.getAuthorizationHeader()!!)
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
