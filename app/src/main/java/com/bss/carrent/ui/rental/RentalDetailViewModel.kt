package com.bss.carrent.ui.rental

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RentalDetailViewModel : ViewModel() {
    private val _isError = MutableLiveData<Boolean>()

    private fun setIsError(value: Boolean) {
        _isError.value = value
    }
}