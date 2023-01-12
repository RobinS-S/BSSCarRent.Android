package com.bss.carrent.ui.car

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.repository.CarRepository
import kotlinx.coroutines.launch

class CarOwnViewModel : ViewModel() {
    private val _carDtoList = MutableLiveData<List<CarDto>>()
    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    private fun setIsError(value: Boolean) {
        _isError.value = value
    }

    val carDtoList: LiveData<List<CarDto>>
        get() = _carDtoList

    private fun setCarList(value: List<CarDto>) {
        _carDtoList.value = value
    }

    fun getMyCars(context: Context) {
        viewModelScope.launch {
            val repository = CarRepository()
            val retrievedCars = repository.getMyCars(context)
            if (retrievedCars == null) {
                setIsError(true)
            } else {
                setIsError(false)
                setCarList(retrievedCars)
            }
        }
    }
}