package com.bss.carrent.api

import com.bss.carrent.api.client.ApiService
import com.bss.carrent.data.user.UserDto
import com.bss.carrent.data.user.UserRegisterDto
import retrofit2.Response
import retrofit2.http.*

interface UserApiService : ApiService {
    @POST("users/profile")
    suspend fun getProfile(@Header("Authorization") authHeader: String): Response<UserDto>

    @POST("users")
    suspend fun register(@Body registerDto: UserRegisterDto): Response<UserDto?>

    @GET("users/{id}")
    suspend fun getUserProfile(@Path("id") id: Long): Response<UserDto>
}