package com.bss.carrent.repository

import android.content.Context
import com.bss.carrent.api.ApiClient
import com.bss.carrent.api.PrefsHelper
import com.bss.carrent.api.UserApiService
import com.bss.carrent.model.User
import java.io.IOException

class ProfileRepository {
    suspend fun attemptLogin(context: Context): User? {
        val profileApiService = ApiClient.createService(context, UserApiService::class.java, "users")

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper != null && prefsHelper.areCredentialsFilled()) {
            try {
                val profile = profileApiService?.getProfile()
                if(profile != null && profile.code() == 200) {
                    return profile.body()
                }
            } catch (e: IOException) {
                return null
            }
        }
        return null
    }
}