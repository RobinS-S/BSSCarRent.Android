package com.bss.carrent.api

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://bsscarrent.azurewebsites.net/api"

object ApiClient {
    fun <T : ApiService> createService(context: Context, serviceClass: Class<T>, controllerName: String): T {
        val prefsHelper = PrefsHelper(context)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val authenticator = object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                if (response.request.header("Authorization") != null) {
                    return response.request
                }
                if (!prefsHelper.areCredentialsFilled()) {
                    return null
                }
                val credential = Credentials.basic(prefsHelper.getUsername()!!, prefsHelper.getPassword()!!)
                return response.request.newBuilder().header("Authorization", credential).build()
            }
        }
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val client = OkHttpClient.Builder()
            .followSslRedirects(false)
            .followRedirects(false)
            .authenticator(authenticator)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(false)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("$BASE_URL/$controllerName/")
            .client(client)
            .build()

        return retrofit.create(serviceClass)
    }
}