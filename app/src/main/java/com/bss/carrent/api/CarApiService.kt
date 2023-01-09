package com.bss.carrent.api

import com.bss.carrent.api.client.ApiClient
import com.bss.carrent.api.client.ApiService
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.data.car.CarUpdateDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CarApiService : ApiService {
    @GET(CarApi.CONTROLLER_NAME)
    suspend fun getCars(): Response<List<CarDto>>

    @GET("${CarApi.CONTROLLER_NAME}/mine")
    suspend fun getMyCars(@Header("Authorization") authHeader: String): Response<List<CarDto>>

    @GET("${CarApi.CONTROLLER_NAME}/{id}")
    suspend fun getCar(@Path("id") id: Long): Response<CarDto>

    @GET("${CarApi.CONTROLLER_NAME}/{id}/tco")
    suspend fun getCarTco(@Path("id") id: Long): Response<Double>

    @GET("${CarApi.CONTROLLER_NAME}/{id}/images")
    suspend fun getCarImageIds(@Path("id") id: Long): Response<List<Long>>

    @POST("${CarApi.CONTROLLER_NAME}/{id}/images")
    suspend fun uploadCarImage(@Header("Authorization") authHeader: String, @Path("id") id: Long, @Part image: MultipartBody.Part): Response<Long>

    @POST("${CarApi.CONTROLLER_NAME}")
    suspend fun createCar(@Header("Authorization") authHeader: String, carDto: CarDto): Response<CarDto>

    @PUT("${CarApi.CONTROLLER_NAME}/{id}")
    suspend fun updateCar(@Header("Authorization") authHeader: String, @Path("id") id: Long, carUpdateDto: CarUpdateDto): Response<CarDto>

    @DELETE("${CarApi.CONTROLLER_NAME}/{id}")
    suspend fun deleteCar(@Header("Authorization") authHeader: String, @Path("id") id: Long): Response<Any>
}

object CarApi {
    const val CONTROLLER_NAME = "cars"

    fun generateImageUrl(carId: Long, imageId: Long): String {
        return "${ApiClient.BASE_URL}${CONTROLLER_NAME}/${carId}/image/${imageId}"
    }
}