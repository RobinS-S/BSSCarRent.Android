package com.bss.carrent.ui.car

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.data.user.UserDto
import com.bss.carrent.repository.CarRepository
import com.bss.carrent.repository.ProfileRepository
import kotlinx.coroutines.launch

class CarDetailViewModel : ViewModel() {
    private val _car = MutableLiveData<CarDto>()
    private val _user = MutableLiveData<UserDto>()
    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    private fun setIsError(value: Boolean) {
        _isError.value = value
    }

    val carDto: LiveData<CarDto>
        get() = _car

    private fun setCar(value: CarDto) {
        _car.value = value
    }

    val userDto: LiveData<UserDto>
        get() = _user

    private fun setUser(value: UserDto) {
        _user.value = value
    }

    fun getCar(context: Context, id: Long) {
        viewModelScope.launch {
            val carRepository = CarRepository()
            val profileRepository = ProfileRepository()
            val retrievedCar = carRepository.getCar(id)
            if (retrievedCar == null) {
                setIsError(true)
            } else {
                setIsError(false)
                setCar(retrievedCar)
                val owner = profileRepository.getProfileForUser(context, retrievedCar.ownerId)
                if (owner != null) {
                    setUser(owner)
                }
            }
        }
    }
}