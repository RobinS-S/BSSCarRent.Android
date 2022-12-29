package com.bss.carrent.ui.car

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bss.carrent.R
import com.google.android.material.card.MaterialCardView

class ReserveActivity : AppCompatActivity() {

    private lateinit var topBar : View
    private lateinit var cardView : MaterialCardView
    private lateinit var carImageView: ImageView
    private lateinit var descriptionTextView: TextView
    private lateinit var reserveCalenderView: CalendarView
    private lateinit var myRentalsButton: Button
    private lateinit var newCarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)

        topBar = findViewById(R.id.topbar_reserve)
        cardView = findViewById(R.id.reserve_carview)
        carImageView = findViewById(R.id.reserve_carimage)
        descriptionTextView = findViewById(R.id.reserve_cardescription)
        reserveCalenderView = findViewById(R.id.reserve_calender)
        myRentalsButton = findViewById(R.id.reserve_back_button)
        newCarButton = findViewById(R.id.reserve_confirm_button)

    }
}