package com.bss.carrent.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bss.carrent.data.Car

class HomeViewModel : ViewModel() {
    val carList = MutableLiveData<List<Car>>()
}