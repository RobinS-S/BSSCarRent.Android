package com.bss.carrent.api.client

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime

object ApiClient {
    const val BASE_URL = "https://bsscarrent.azurewebsites.net/api/"

    fun <T : ApiService> createService(
        serviceClass: Class<T>
    ): T {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDate::class.java, LocalDateJsonAdapter())
            .add(LocalDateTime::class.java, LocalDateTimeJsonAdapter())
            .build()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val client = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(false)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(client.build())
            .build()

        return retrofit.create(serviceClass)
    }
}

