package com.bss.carrent.ui.invoice

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bss.carrent.R

class InvoiceActivity : AppCompatActivity() {

    private lateinit var topBar : View
    private lateinit var invoiceTextView: TextView
    private lateinit var invoicesButton : Button
    private lateinit var payInvoiceButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        topBar = findViewById(R.id.topbar_invoices)
        invoiceTextView = findViewById(R.id.invoice_textview)
        invoicesButton = findViewById(R.id.invoices_btn)
        payInvoiceButton = findViewById(R.id.pay_invoice_btn)

    }
}