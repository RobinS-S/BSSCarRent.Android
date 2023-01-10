package com.bss.carrent.api

import com.bss.carrent.api.client.ApiService
import com.bss.carrent.data.InvoiceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface InvoiceApiService : ApiService {
    @GET("invoice/mine")
    suspend fun getMyInvoices(@Header("Authorization") authHeader: String): Response<List<InvoiceDto>>

    @GET("invoice/owned")
    suspend fun getOwnedInvoices(@Header("Authorization") authHeader: String): Response<List<InvoiceDto>>

    @GET("invoice/{id}/pay")
    suspend fun payInvoice(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Response<InvoiceDto>
}