package com.bss.carrent.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarDetailViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "..."
    }

    val text: LiveData<String> = _text

    fun setText(value: String) {
        _text.value = value
    }
}