package com.bss.carrent.repository

import android.content.Context
import com.bss.carrent.api.UserApiService
import com.bss.carrent.api.client.ApiClient
import com.bss.carrent.data.user.UserDto
import com.bss.carrent.data.user.UserRegisterDto
import com.bss.carrent.misc.PrefsHelper
import java.io.IOException

class ProfileRepository {
    suspend fun attemptLogin(context: Context): UserDto? {
        val profileApiService =
            ApiClient.createService(UserApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val profile = profileApiService.getProfile(prefsHelper.getAuthorizationHeader()!!)
                if (profile.code() == 200) {
                    profile.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun getProfileForUser(userId: Long, context: Context): UserDto? {
        val profileApiService =
            ApiClient.createService(UserApiService::class.java, context)

        return try {
            val profile = profileApiService.getUserProfile(userId)
            if (profile.code() == 200) {
                profile.body()
            } else throw IOException()
        } catch (e: IOException) {
            null
        }
    }

    suspend fun register(registerDto: UserRegisterDto, context: Context): UserDto? {
        val profileApiService =
            ApiClient.createService(UserApiService::class.java, context)
        return try {
            val car = profileApiService.register(registerDto)
            if (car.code() == 200) {
                car.body()
            } else throw IOException()
        } catch (e: IOException) {
            null
        }
    }
}