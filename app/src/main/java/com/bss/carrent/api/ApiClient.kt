package com.bss.carrent.api

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDate

private const val BASE_URL = "https://bsscarrent.azurewebsites.net/api/"

object ApiClient {
    fun <T : ApiService> createService(
        context: Context,
        serviceClass: Class<T>
    ): T {
        val prefsHelper = PrefsHelper(context)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDate::class.java, LocalDateJsonAdapter())
            .build()

        val authenticator = Authentication(prefsHelper.getUsername(), prefsHelper.getPassword())
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val client = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(false)
            .authenticator(authenticator)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(client.build())
            .build()

        return retrofit.create(serviceClass)
    }
}

