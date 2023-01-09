package com.bss.carrent.api

import com.bss.carrent.api.client.ApiService
import com.bss.carrent.data.rental.RentalDto
import com.bss.carrent.data.rental.RentalPeriodDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RentalApiService : ApiService {
    @GET("rentals/car/{id}")
    suspend fun getCarRentals(@Header("Authorization") authHeader: String, @Path("id") id: Long): Response<List<RentalDto>>

    @GET("rentals/car/{id}")
    suspend fun getCarRentalPeriods(@Header("Authorization") authHeader: String, @Path("id") id: Long): Response<List<RentalPeriodDto>>
}