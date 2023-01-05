package com.bss.carrent.repository

import android.content.Context
import com.bss.carrent.api.ApiClient
import com.bss.carrent.api.PrefsHelper
import com.bss.carrent.api.UserApiService
import com.bss.carrent.model.User

class ProfileRepository {
    suspend fun attemptLogin(context: Context): User? {
        val profileApiService = ApiClient.createService(context, UserApiService::class.java, "users")

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper != null && prefsHelper.areCredentialsFilled()) {
            val profile = profileApiService?.getProfile()
            if(profile != null) {
                if(profile.code() == 200) {
                    return profile.body()
                }
                else {
                }
            }
        }
        return null
    }
}