package com.bss.carrent.ui.rental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalDateTime

class RentalCreateViewModel : ViewModel() {
    private val _reservedFromDate = MutableLiveData<LocalDate>()
    private val _reservedUntilDate = MutableLiveData<LocalDate>()
    private val _reservedFromTime = MutableLiveData<LocalDateTime>()
    private val _reservedUntilTime = MutableLiveData<LocalDateTime>()
    private val _kmCount = MutableLiveData<Int>()

    val reservedFromDate: LiveData<LocalDate>
        get() = _reservedFromDate

    private fun setReservedFromDate(value: LocalDate) {
        _reservedFromDate.value = value
    }

    val reservedUntilDate: LiveData<LocalDate>
        get() = _reservedUntilDate

    private fun setReservedUntilDate(value: LocalDate) {
        _reservedUntilDate.value = value
    }

    val reservedFromTime: LiveData<LocalDateTime>
        get() = _reservedFromTime

    private fun setReservedFromTime(value: LocalDateTime) {
        _reservedFromTime.value = value
    }

    val reservedUntilTime: LiveData<LocalDateTime>
        get() = _reservedUntilTime

    private fun setReservedUntilTime(value: LocalDateTime) {
        _reservedUntilTime.value = value
    }

    val kmCount: LiveData<Int>
        get() = _kmCount

    private fun setKmCount(value: Int) {
        _kmCount.value = value
    }
}