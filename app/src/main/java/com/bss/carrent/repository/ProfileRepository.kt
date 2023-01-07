package com.bss.carrent.repository

import android.content.Context
import com.bss.carrent.api.ApiClient
import com.bss.carrent.api.PrefsHelper
import com.bss.carrent.api.UserApiService
import com.bss.carrent.data.User
import java.io.IOException

class ProfileRepository {
    suspend fun attemptLogin(context: Context): User? {
        val profileApiService =
            ApiClient.createService(context, UserApiService::class.java)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val profile = profileApiService.getProfile()
                if (profile.code() == 200) {
                    profile.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }
}