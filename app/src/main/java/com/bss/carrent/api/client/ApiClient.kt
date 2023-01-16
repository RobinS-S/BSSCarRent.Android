package com.bss.carrent.api.client

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.bss.carrent.data.ApiError
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.nio.charset.StandardCharsets.UTF_8
import java.time.LocalDate
import java.time.LocalDateTime

object ApiClient {
    const val BASE_URL = "https://bsscarrent.azurewebsites.net/api/"

    fun <T : ApiService> createService(
        serviceClass: Class<T>,
        context: Context
    ): T {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDate::class.java, LocalDateJsonAdapter())
            .add(LocalDateTime::class.java, LocalDateTimeJsonAdapter())
            .build()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val adapter = moshi.adapter(ApiError::class.java)
        val interceptor = Interceptor { chain ->
            val handler = Handler(Looper.getMainLooper())
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.code == 403 || response.code == 400) {
                val source = response.body?.source()
                source?.request(Long.MAX_VALUE)
                val buffer = source?.buffer()
                val jsonString = buffer?.clone()?.readString(UTF_8)
                val errorMessage = adapter.fromJson(jsonString)
                if(errorMessage?.error != null) {
                    handler.post {
                        Toast.makeText(context, errorMessage?.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
            response
        }
        val client = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(false)
            .addInterceptor(interceptor)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(client.build())
            .build()

        return retrofit.create(serviceClass)
    }
}

