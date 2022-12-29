package com.bss.carrent.ui.car

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bss.carrent.R

class NewCarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Find views
        val topbar = findViewById<View>(R.id.topbar_newcar)
        val container = findViewById<ConstraintLayout>(R.id.container)
        val imageFirst = findViewById<ImageView>(R.id.newcar_image_first)
        val imageSecond = findViewById<ImageView>(R.id.newcar_image_second)
        val invoiceTextView = findViewById<TextView>(R.id.newcar_invoicedetails)
        val myRentalsButton = findViewById<Button>(R.id.newcar_myrentals)
        val newCarButton = findViewById<Button>(R.id.newcar_savecar)

        // Set up views
        /*imageFirst.setImageResource(R.drawable.ic_image_first)
        imageSecond.setImageResource(R.drawable.ic_image_second)
        invoiceTextView.text = getString(R.string.invoice_details)*/

        myRentalsButton.setOnClickListener {
        }

        newCarButton.setOnClickListener {
        }

    }
}