package com.bss.carrent.ui.rental

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.rental.RentalCreateDto
import com.bss.carrent.data.rental.RentalDto
import com.bss.carrent.data.rental.RentalPeriodDto
import com.bss.carrent.repository.RentalRepository
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class RentalCreateViewModel : ViewModel() {
    private val _reservedFromDate = MutableLiveData<LocalDate>()
    private val _reservedUntilDate = MutableLiveData<LocalDate>()
    private val _reservedFromTime = MutableLiveData<LocalTime>()
    private val _reservedUntilTime = MutableLiveData<LocalTime>()
    private val _kmCount = MutableLiveData<Int>()
    private val _hoursCost = MutableLiveData<Double>()
    private val _usedTimeSlots = MutableLiveData<List<RentalPeriodDto>>()
    private val _totalPrice = MutableLiveData<Double>()
    private val _createdRental = MutableLiveData<RentalDto>()

    val reservedFromDate: LiveData<LocalDate>
        get() = _reservedFromDate

    fun setReservedFromDate(value: LocalDate) {
        _reservedFromDate.value = value
    }

    val reservedUntilDate: LiveData<LocalDate>
        get() = _reservedUntilDate

    fun setReservedUntilDate(value: LocalDate) {
        _reservedUntilDate.value = value
    }

    val reservedFromTime: LiveData<LocalTime>
        get() = _reservedFromTime

    fun setReservedFromTime(value: LocalTime) {
        _reservedFromTime.value = value
    }

    val reservedUntilTime: LiveData<LocalTime>
        get() = _reservedUntilTime

    fun setReservedUntilTime(value: LocalTime) {
        _reservedUntilTime.value = value
    }

    val kmCount: LiveData<Int>
        get() = _kmCount

    fun setKmCount(value: Int) {
        _kmCount.value = value
    }

    val hoursCost: LiveData<Double>
        get() = _hoursCost

    val usedTimeSlots: LiveData<List<RentalPeriodDto>>
        get() = _usedTimeSlots

    val totalPrice: LiveData<Double>
        get() = _totalPrice

    fun setTotalPrice(value: Double) {
        this._totalPrice.value = value
    }

    val createdRental: LiveData<RentalDto>
        get() = _createdRental

    fun getUsedSlots(context: Context, carId: Long) {
        viewModelScope.launch {
            val repository = RentalRepository()
            val usedSlots = repository.getPeriodsForCarId(context, carId)
            if (usedSlots != null) {
                val comparator = compareBy<RentalPeriodDto> { it.reservedFrom }
                usedSlots.sortedWith(comparator)
                _usedTimeSlots.value = usedSlots!!
            }
        }
    }

    fun isCurrentTimeDateAvailable(): Boolean? {
        val from = getFullFromDateTime()
        val until = getFullUntilDateTime()
        if (from == null || until == null) return null
        if (until.isBefore(from)) return null
        if (_usedTimeSlots.value == null) return null
        for (slot in _usedTimeSlots.value!!) {
            if (isBetween(slot.reservedFrom, from, until)) return false
            if (isBetween(slot.reservedUntil, from, until)) return false
        }
        return true
    }

    private fun isBetween(
        checkDateTime: LocalDateTime,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime
    ): Boolean {
        return checkDateTime.isAfter(startDateTime) && checkDateTime.isBefore(endDateTime) || checkDateTime.isEqual(
            startDateTime
        ) || checkDateTime.isEqual(endDateTime)
    }

    fun calculateHoursCost(carHourCost: Double): Double? {
        if (reservedFromTime.value == null || reservedFromDate.value == null || reservedUntilTime.value == null || reservedUntilDate.value == null) return null
        val duration = Duration.between(getFullFromDateTime(), getFullUntilDateTime())
        if (duration.isNegative) return null
        val result = duration.toHours() * carHourCost
        _hoursCost.value = result
        return result
    }

    fun getFullFromDateTime(): LocalDateTime? {
        if (this.reservedFromDate.value == null || this.reservedFromTime.value == null) return null
        return LocalDateTime.of(this.reservedFromDate.value, this.reservedFromTime.value)
    }

    fun getFullUntilDateTime(): LocalDateTime? {
        if (this.reservedUntilDate.value == null || this.reservedUntilTime.value == null) return null
        return LocalDateTime.of(this.reservedUntilDate.value, this.reservedUntilTime.value)
    }

    fun createDto(carId: Long): RentalCreateDto? {
        val from = getFullFromDateTime()
        val until = getFullUntilDateTime()
        if(from == null || until == null || kmCount.value == null) return null
        val dto = RentalCreateDto(from, until, kmCount.value!!.toLong(), carId)
        return dto
    }

    fun post(context: Context, dto: RentalCreateDto) {
        viewModelScope.launch {
            val repository = RentalRepository()
            val createdRental = repository.createRental(context, dto)
            if (createdRental != null) {
                _createdRental.value = createdRental!!
            } else {
                Toast.makeText(context, "Error while creating", Toast.LENGTH_LONG).show()
            }
        }
    }
}