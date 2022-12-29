package com.bss.carrent.ui.car

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bss.carrent.R

class CarInfoActivity : AppCompatActivity() {

    private lateinit var topBar: View
    private lateinit var imageFirst: ImageView
    private lateinit var imageSecond: ImageView
    private lateinit var invoiceTextView: TextView
    private lateinit var editCarButton: Button
    private lateinit var reserveCarButton: Button
    private lateinit var deleteCarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carinfo)

        topBar = findViewById(R.id.topbar_carinfo)
        imageFirst = findViewById(R.id.carinfo_carimage_first)
        imageSecond = findViewById(R.id.carinfo_carimage_second)
        invoiceTextView = findViewById(R.id.carinfo_invoice_details)
        editCarButton = findViewById(R.id.carinfo_editcar)
        reserveCarButton = findViewById(R.id.carinfo_reservecar)
        deleteCarButton = findViewById(R.id.carinfo_deletecar)
    }
}