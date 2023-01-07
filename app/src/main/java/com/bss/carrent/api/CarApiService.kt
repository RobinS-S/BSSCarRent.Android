package com.bss.carrent.api

import com.bss.carrent.data.Car
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CarApiService : ApiService {
    @GET
    suspend fun getCars(): Response<List<Car>>

    @GET("/{id}")
    suspend fun getCar(@Path("id") id: Long): Response<Car>
}