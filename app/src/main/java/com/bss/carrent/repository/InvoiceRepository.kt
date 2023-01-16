package com.bss.carrent.repository

import android.content.Context
import com.bss.carrent.api.InvoiceApiService
import com.bss.carrent.api.client.ApiClient
import com.bss.carrent.data.InvoiceDto
import com.bss.carrent.misc.PrefsHelper
import java.io.IOException

class InvoiceRepository {
    suspend fun getMyInvoices(context: Context): List<InvoiceDto>? {
        val invoiceApiService =
            ApiClient.createService(InvoiceApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val invoices =
                    invoiceApiService.getMyInvoices(prefsHelper.getAuthorizationHeader()!!)
                if (invoices.code() == 200) {
                    invoices.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun getOwnedInvoices(context: Context): List<InvoiceDto>? {
        val invoiceApiService =
            ApiClient.createService(InvoiceApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val invoices =
                    invoiceApiService.getOwnedInvoices(prefsHelper.getAuthorizationHeader()!!)
                if (invoices.code() == 200) {
                    invoices.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }

    suspend fun payInvoice(context: Context, id: Long): InvoiceDto? {
        val invoiceApiService =
            ApiClient.createService(InvoiceApiService::class.java, context)

        val prefsHelper = PrefsHelper(context)
        if (prefsHelper.areCredentialsFilled()) {
            return try {
                val invoice =
                    invoiceApiService.payInvoice(prefsHelper.getAuthorizationHeader()!!, id)
                if (invoice.code() == 200) {
                    invoice.body()
                } else throw IOException()
            } catch (e: IOException) {
                null
            }
        }
        return null
    }
}