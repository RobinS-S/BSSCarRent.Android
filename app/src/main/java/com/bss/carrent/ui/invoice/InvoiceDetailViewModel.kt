package com.bss.carrent.ui.invoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InvoiceDetailViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "..."
    }

    val text: LiveData<String> = _text

    fun setText(value: Long) {
        _text.value = value.toString()
    }
}