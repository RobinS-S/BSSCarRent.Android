package com.bss.carrent.api

import com.bss.carrent.api.client.ApiService
import com.bss.carrent.data.user.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService : ApiService {
    @POST("users/profile")
    suspend fun getProfile(@Header("Authorization") authHeader: String): Response<UserDto>

    @GET("users/{id}")
    suspend fun getUserProfile(@Path("id") id: Long): Response<UserDto>
}