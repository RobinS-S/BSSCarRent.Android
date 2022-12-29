package com.bss.carrent.ui.car


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bss.carrent.R

class CarListActivity : AppCompatActivity() {

    private lateinit var topBar: View
    private lateinit var cardView: MaterialCardView
    private lateinit var carListImage: ImageView
    private lateinit var carDescriptionTextView: TextView
    private lateinit var myRentalsButton: Button
    private lateinit var newCarButton: Button
    private lateinit var rentedCarsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topBar = findViewById(R.id.topbar_carlist)
        cardView = findViewById(R.id.carlist_cardview)
        carListImage = findViewById(R.id.carlist_carimage)
        carDescriptionTextView = findViewById(R.id.carlist_cardescription)
        myRentalsButton = findViewById(R.id.carlist_rentals)
        newCarButton = findViewById(R.id.carlist_newcar)
        rentedCarsButton = findViewById(R.id.carlist_rentedcars)
    }
}