package com.bss.carrent.ui.invoice

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bss.carrent.R

class InvoicesActivity : AppCompatActivity() {

    private lateinit var topBar : View
    private lateinit var invoiceDetailsTextView: TextView
    private lateinit var payInvoiceButton : Button
    private lateinit var myRentalsButton : Button
    private lateinit var rentedInvoicesButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoices)

        topBar = findViewById(R.id.topbar_invoices)
        invoiceDetailsTextView = findViewById(R.id.invoice_details)
        payInvoiceButton = findViewById(R.id.pay_invoice_button)
        myRentalsButton = findViewById(R.id.my_rentals_invoices)
        rentedInvoicesButton = findViewById(R.id.rented_invoices)

    }
}