package com.bss.carrent.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.Car
import com.bss.carrent.repository.CarRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _carList = MutableLiveData<List<Car>>()
    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    private fun setIsError(value: Boolean) {
        _isError.value = value
    }

    val carList: LiveData<List<Car>>
        get() = _carList

    private fun setCarList(value: List<Car>) {
        _carList.value = value
    }

    fun getCars(context: Context) {
        viewModelScope.launch {
            val repository = CarRepository()
            val retrievedCars = repository.getAll(context)
            if(retrievedCars == null) {
                setIsError(true)
            } else {
                setIsError(false)
                setCarList(retrievedCars)
            }
        }
    }
}