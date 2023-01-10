package com.bss.carrent.repository

import android.content.Context
import com.bss.carrent.api.CarApiService
import com.bss.carrent.api.client.ApiClient
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.data.car.CarUpdateDto
import com.bss.carrent.misc.AuthHelper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class CarRepository {
    suspend fun getAll(): List<CarDto>? {
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

    suspend fun getMyCars(context: Context): List<CarDto>? {
        val carApiService =
            ApiClient.createService(CarApiService::class.java)

        val authHelper = AuthHelper(context)
        if (authHelper.areCredentialsFilled()) {
            return try {
                val cars = carApiService.getMyCars(authHelper.getAuthorizationHeader()!!)
                if (cars.code() == 200) {
                    cars.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun getCar(id: Long): CarDto? {
        val carApiService =
            ApiClient.createService(CarApiService::class.java)

        return try {
            val cars = carApiService.getCar(id)
            cars.body()
        } catch (e: IOException) {
            null
        }
    }

    suspend fun getCarTco(id: Long): Double? {
        val carApiService =
            ApiClient.createService(CarApiService::class.java)

        return try {
            val tco = carApiService.getCarTco(id)
            tco.body()
        } catch (e: IOException) {
            null
        }
    }

    suspend fun createCar(context: Context, carDto: CarDto): CarDto? {
        val carApiService =
            ApiClient.createService(CarApiService::class.java)
        val authHelper = AuthHelper(context)

        if (authHelper.areCredentialsFilled()) {
            return try {
                val car = carApiService.createCar(authHelper.getAuthorizationHeader()!!, carDto)
                if (car.code() == 200) {
                    car.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun updateCar(context: Context, id: Long, carUpdateDto: CarUpdateDto): CarDto? {
        val carApiService =
            ApiClient.createService(CarApiService::class.java)
        val authHelper = AuthHelper(context)

        if (authHelper.areCredentialsFilled()) {
            return try {
                val car =
                    carApiService.updateCar(authHelper.getAuthorizationHeader()!!, id, carUpdateDto)
                if (car.code() == 200) {
                    car.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun deleteCar(context: Context, id: Long): Any? {
        val carApiService =
            ApiClient.createService(CarApiService::class.java)
        val authHelper = AuthHelper(context)

        if (authHelper.areCredentialsFilled()) {
            return try {
                val car = carApiService.deleteCar(authHelper.getAuthorizationHeader()!!, id)
                if (car.code() == 200) {
                    car.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun uploadCarImage(context: Context, id: Long, image: File): Long? {
        val carApiService =
            ApiClient.createService(CarApiService::class.java)
        val authHelper = AuthHelper(context)

        if (authHelper.areCredentialsFilled()) {
            return try {
                val imagePart = MultipartBody.Part.createFormData(
                    "image", image.name,
                    image.asRequestBody("image/*".toMediaTypeOrNull())
                )
                val imgId = carApiService.uploadCarImage(
                    authHelper.getAuthorizationHeader()!!,
                    id,
                    imagePart
                )
                if (imgId.code() == 200) {
                    imgId.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }
}