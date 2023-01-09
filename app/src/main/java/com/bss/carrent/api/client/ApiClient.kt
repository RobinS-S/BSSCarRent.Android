package com.bss.carrent.api.client

import android.content.Context
import com.bss.carrent.misc.AuthHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDate

object ApiClient {
    const val BASE_URL = "https://bsscarrent.azurewebsites.net/api/"

    fun <T : ApiService> createService(
        serviceClass: Class<T>
    ): T {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDate::class.java, LocalDateJsonAdapter())
            .build()

        //val authenticator = Authentication(authHelper.getUsername(), authHelper.getPassword())
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val client = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(false)
            //.authenticator(authenticator)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(client.build())
            .build()

        return retrofit.create(serviceClass)
    }
}

