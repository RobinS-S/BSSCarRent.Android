package com.bss.carrent.ui.car

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.data.car.CarUpdateDto
import com.bss.carrent.repository.CarRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class CarEditViewModel : ViewModel() {
    private val _carDto = MutableLiveData<CarDto>()
    private val _isError = MutableLiveData<Boolean>()
    private val _updated = MutableLiveData<CarDto>()

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

    val updatedCar: LiveData<CarDto>
        get() = _updated

    fun getCar(id: Long, context: Context) {
        viewModelScope.launch {
            val repository = CarRepository()
            val retrievedCar = repository.getCar(id, context)
            if (retrievedCar == null) {
                setIsError(true)
            } else {
                setIsError(false)
                setCar(retrievedCar)
            }
        }
    }

    fun updateCar(
        context: Context,
        id: Long,
        dto: CarUpdateDto) {
        viewModelScope.launch {
            val repository = CarRepository()
            val updatedCar = repository.updateCar(context, id, dto)
            if (updatedCar == null) {
                setIsError(true)
            } else {
                setIsError(false)
                _updated.value = updatedCar!!
            }
        }
    }
}