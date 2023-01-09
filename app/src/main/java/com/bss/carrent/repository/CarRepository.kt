package com.bss.carrent.repository

import android.content.Context
import com.bss.carrent.api.CarApiService
import com.bss.carrent.api.client.ApiClient
import com.bss.carrent.data.car.CarDto
import java.io.IOException

class CarRepository {
    suspend fun getAll(context: Context): List<CarDto>? {
        val carApiService =
            ApiClient.createService(CarApiService::class.java)

        return try {
            val cars = carApiService.getCars()
            if (cars.code() == 200) {
                return cars.body()
            } else throw IOException()
        } catch (e: IOException) {
            null
        }
    }

    suspend fun getCar(context: Context, id: Long): CarDto? {
        val carApiService =
            ApiClient.createService(CarApiService::class.java)

        return try {
            val cars = carApiService.getCar(id)
            cars.body()
        } catch (e: IOException) {
            null
        }
    }
}