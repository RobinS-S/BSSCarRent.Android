package com.bss.carrent

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bss.carrent.databinding.ActivityMainBinding
import com.bss.carrent.misc.PrefsHelper
import com.bss.carrent.misc.Helpers
import com.bss.carrent.ui.auth.LoginViewModel
import com.google.android.material.navigation.NavigationView
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var viewModel: LoginViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefsHelper: PrefsHelper
    private lateinit var switchAutoTheme: SwitchCompat
    private lateinit var switchDarkTheme: SwitchCompat

    private lateinit var sensorManager: SensorManager
    private lateinit var lightSensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            this.setTheme(R.style.Theme_BSSCarRent);
        }
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            this.installSplashScreen();
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Amsterdam"))

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_car_list,
                R.id.nav_rentals,
                R.id.nav_invoices,
                R.id.nav_register,
                R.id.nav_login,
                R.id.switch_auto_theme,
                R.id.switch_dark_theme,
                R.id.nav_preferences
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        prefsHelper = PrefsHelper(applicationContext)

        viewModel.userDto.observe(this) { user ->
            val headerLayout = navView.getHeaderView(0)
            val textView = headerLayout.findViewById<TextView>(R.id.subNavTitle)
            if (user == null) {
                textView.setText(R.string.nav_header_subtitle)
                prefsHelper.resetCredentials()
            } else {
                textView.text = "Logged in as ${Helpers.getFormattedName(user)}"
            }
        }
        if (prefsHelper.areCredentialsFilled()) {
            try {
                viewModel.tryLogin(applicationContext, true)
            } catch (e: IOException) {
                viewModel.setUser(null)
            }
        }
        prefsHelper.loadTheme()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_LIGHT) {
            val theme = this.prefsHelper.getTheme()
            if (theme == "auto") {
                val lux = event.values[0]
                if (lux > 30000) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchDarkTheme.isChecked = false
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchDarkTheme.isChecked = true
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}