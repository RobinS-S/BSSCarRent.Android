package com.bss.carrent.ui.rental

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.InvoiceDto
import com.bss.carrent.data.rental.RentalDto
import com.bss.carrent.repository.RentalRepository
import kotlinx.coroutines.launch

class RentalDetailViewModel : ViewModel() {
    private val _isError = MutableLiveData<Boolean>()
    private val _cancelled = MutableLiveData<RentalDto>()
    private val _pickup = MutableLiveData<RentalDto>()
    private val _invoice = MutableLiveData<InvoiceDto>()

    val isCancelled: LiveData<RentalDto>
        get() = _cancelled

    val pickup: LiveData<RentalDto>
        get() = _cancelled

    val invoice: LiveData<InvoiceDto>
        get() = _invoice

    private fun setIsError(value: Boolean) {
        _isError.value = value
    }

    fun cancel(context: Context) {
        viewModelScope.launch {
            val repository = RentalRepository()
            val rental = repository.deleteCurrentRental(context)
            if (rental == null) {
                setIsError(true)
            } else {
                setIsError(false)
                _cancelled.value = rental!!
            }
        }
    }

    fun pickup(context: Context, id: Long) {
        viewModelScope.launch {
            val repository = RentalRepository()
            val rental = repository.markRentalAsPickedUp(context, id)
            if (rental == null) {
                setIsError(true)
            } else {
                setIsError(false)
                _pickup.value = rental!!
            }
        }
    }

    fun deliver(context: Context, id: Long) {
        viewModelScope.launch {
            val repository = RentalRepository()
            val invoice = repository.markRentalAsDelivered(context, id)
            if (invoice == null) {
                setIsError(true)
            } else {
                setIsError(false)
                _invoice.value = invoice!!
            }
        }
    }
}