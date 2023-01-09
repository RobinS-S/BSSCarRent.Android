package com.bss.carrent.api

import com.bss.carrent.api.client.ApiService
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.data.car.CarUpdateDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CarApiService : ApiService {
    @GET("cars")
    suspend fun getCars(): Response<List<CarDto>>

    @GET("cars/mine")
    suspend fun getMyCars(@Header("Authorization") authHeader: String): Response<List<CarDto>>

    @GET("cars/{id}")
    suspend fun getCar(@Path("id") id: Long): Response<CarDto>

    @GET("cars/{id}/tco")
    suspend fun getCarTco(@Path("id") id: Long): Response<Double>

    @GET("cars/{id}/images")
    suspend fun getCarImageIds(@Path("id") id: Long): Response<List<Long>>

    @POST("cars/{id}/images")
    suspend fun uploadCarImage(@Header("Authorization") authHeader: String, @Path("id") id: Long, @Part image: MultipartBody.Part): Response<Long>

    @POST("cars")
    suspend fun createCar(@Header("Authorization") authHeader: String, carDto: CarDto): Response<CarDto>

    @PUT("cars/{id}")
    suspend fun updateCar(@Header("Authorization") authHeader: String, @Path("id") id: Long, carUpdateDto: CarUpdateDto): Response<CarDto>

    @DELETE("cars/{id}")
    suspend fun deleteCar(@Header("Authorization") authHeader: String, @Path("id") id: Long): Response<Any>
}