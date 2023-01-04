package com.bss.carrent.api

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://bsscarrent.azurewebsites.net/api"

class ApiClient(context: Context) {
    private val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
    private val client: OkHttpClient
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    init {
        val authenticator = object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                if (response.request.header("Authorization") != null) {
                    return response.request
                }
                val username = prefs.getString("username", "")
                val password = prefs.getString("password", "")
                if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                    return null
                }
                val credential = Credentials.basic(username, password)
                return response.request.newBuilder().header("Authorization", credential).build()
            }
        }
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        client = OkHttpClient.Builder()
            .followSslRedirects(false)
            .followRedirects(false)
            .authenticator(authenticator)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(false)
            .build()
    }

    fun <T : ApiService> createService(serviceClass: Class<T>, controllerName: String): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("$BASE_URL/$controllerName/")
            .client(client)
            .build()
        return retrofit.create(serviceClass)
    }
}