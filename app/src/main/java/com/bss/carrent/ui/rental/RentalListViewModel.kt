package com.bss.carrent.ui.rental

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.rental.RentalDto
import com.bss.carrent.repository.RentalRepository
import kotlinx.coroutines.launch

class RentalListViewModel : ViewModel() {
    private val _rentalDtoList = MutableLiveData<List<RentalDto>>()
    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    private fun setIsError(value: Boolean) {
        _isError.value = value
    }

    val rentalDtoList: LiveData<List<RentalDto>>
        get() = _rentalDtoList

    private fun setRentalList(value: List<RentalDto>) {
        _rentalDtoList.value = value
    }

    fun getRentals(context: Context, value: String) {
        viewModelScope.launch {
            val repository = RentalRepository()
            val rentals =
                if (value == "mine") repository.getMine(context) else repository.getOwned(context)
            if (rentals == null) {
                setIsError(true)
            } else {
                setIsError(false)
                setRentalList(rentals)
            }
        }
    }
}