package com.bss.carrent.ui.car

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.repository.CarRepository
import kotlinx.coroutines.launch

class CarEditViewModel : ViewModel() {
    private val _carDto = MutableLiveData<CarDto>()
    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    private fun setIsError(value: Boolean) {
        _isError.value = value
    }

    val carDtoList: LiveData<CarDto>
        get() = _carDto

    private fun setCar(value: CarDto) {
        _carDto.value = value
    }

    fun getCar(id: Long) {
        viewModelScope.launch {
            val repository = CarRepository()
            val retrievedCar = repository.getCar(id)
            if (retrievedCar == null) {
                setIsError(true)
            } else {
                setIsError(false)
                setCar(retrievedCar)
            }
        }
    }
}