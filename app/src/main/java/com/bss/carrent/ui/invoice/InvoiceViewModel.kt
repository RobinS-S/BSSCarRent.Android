package com.bss.carrent.ui.invoice

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.Invoice
import com.bss.carrent.repository.InvoiceRepository
import kotlinx.coroutines.launch

class InvoiceViewModel : ViewModel() {
    private val _invoiceList = MutableLiveData<List<Invoice>>()
    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    private fun setIsError(value: Boolean) {
        _isError.value = value
    }

    val invoiceList: LiveData<List<Invoice>>
        get() = _invoiceList

    private fun setInvoiceList(value: List<Invoice>) {
        _invoiceList.value = value
    }

    fun getInvoices(context: Context) {
        viewModelScope.launch {
            val repository = InvoiceRepository()
            val retrievedInvoices = repository.getAll(context)
            if(retrievedInvoices == null) {
                setIsError(true)
            } else {
                setIsError(false)
                setInvoiceList(retrievedInvoices)
            }
        }
    }
}