package com.bss.carrent.ui.invoice

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.bss.carrent.R
import com.bss.carrent.databinding.ActivityPayBinding

class PayActivity : AppCompatActivity() {

    private var _binding: ActivityPayBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

    }
}