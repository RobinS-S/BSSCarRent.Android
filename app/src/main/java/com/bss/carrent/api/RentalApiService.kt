package com.bss.carrent.api

import com.bss.carrent.api.client.ApiService
import com.bss.carrent.data.InvoiceDto
import com.bss.carrent.data.rental.RentalCreateDto
import com.bss.carrent.data.rental.RentalDeliverDto
import com.bss.carrent.data.rental.RentalDto
import com.bss.carrent.data.rental.RentalPeriodDto
import retrofit2.Response
import retrofit2.http.*

interface RentalApiService : ApiService {
    @GET("rentals/car/{id}")
    suspend fun getCarRentals(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Response<List<RentalDto>>

    @GET("rentals/car/{id}/periods")
    suspend fun getCarRentalPeriods(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Response<List<RentalPeriodDto>>

    @GET("rentals/owned")
    suspend fun getOwnedRentals(@Header("Authorization") authHeader: String): Response<List<RentalDto>>

    @GET("rentals/mine")
    suspend fun getMyRentals(@Header("Authorization") authHeader: String): Response<List<RentalDto>>

    @POST("rentals")
    suspend fun createRental(
        @Header("Authorization") authHeader: String,
        @Body rentalDto: RentalCreateDto
    ): Response<RentalDto>

    @POST("rentals/{id}/markPickedUp")
    suspend fun markRentalAsPickedUp(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Response<RentalDto>

    @POST("rentals/{id}/markDelivered")
    suspend fun markRentalAsDelivered(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long,
        @Body deliverDto: RentalDeliverDto
    ): Response<InvoiceDto>

    @GET("rentals/current")
    suspend fun getCurrentRental(@Header("Authorization") authHeader: String): Response<RentalDto?>

    @DELETE("rentals/current")
    suspend fun deleteCurrentRental(@Header("Authorization") authHeader: String): Response<RentalDto?>
}