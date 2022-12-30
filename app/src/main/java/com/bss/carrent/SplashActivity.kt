package com.bss.carrent.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bss.carrent.R
import android.content.Intent
import android.os.CountDownTimer
import com.bss.carrent.MainActivity

class SplashActivity : AppCompatActivity() {

    private val splashDuration: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // timer
        object : CountDownTimer(splashDuration, 2000) {
            override fun onTick(millisUntilFinished: Long) {
                // TODO: add some developers info?
            }

            override fun onFinish() {
                // Start the main activity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                // Finish this activity
                finish()
            }
        }.start()
    }
}
