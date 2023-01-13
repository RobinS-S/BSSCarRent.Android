package com.bss.carrent.ui.invoice

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bss.carrent.data.InvoiceDto
import com.bss.carrent.repository.InvoiceRepository


class InvoiceDetailViewModel : ViewModel() {

    suspend fun payInvoice(context: Context, id: Long): InvoiceDto? {
        val repository = InvoiceRepository()
        return repository.payInvoice(context, id)
    }
}