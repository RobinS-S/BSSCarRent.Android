package com.bss.carrent.api

import com.bss.carrent.data.model.User
import retrofit2.Response
import retrofit2.http.POST

interface UserApiService : ApiService {
    @POST("profile")
    suspend fun getProfile(): Response<User>
}
