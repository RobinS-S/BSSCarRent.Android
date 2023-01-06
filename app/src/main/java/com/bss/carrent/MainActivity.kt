package com.bss.carrent

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bss.carrent.api.PrefsHelper
import com.bss.carrent.databinding.ActivityMainBinding
import com.bss.carrent.misc.Helpers
import com.bss.carrent.viewmodel.LoginViewModel
import java.io.IOException
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefsHelper: PrefsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_login
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        prefsHelper = PrefsHelper(applicationContext)

        viewModel.user.observe(this) { user ->
            val navView = findViewById<NavigationView>(R.id.nav_view)
            val headerLayout = navView.getHeaderView(0)
            val textView = headerLayout.findViewById<TextView>(R.id.subNavTitle)
            if(user == null) {
                textView.setText(R.string.nav_header_subtitle)
                prefsHelper.reset()
            } else {
                textView.text = "Logged in as ${Helpers.getFormattedName(user)}"
            }
        }
        if(prefsHelper.areCredentialsFilled()) {
            try {
                viewModel.tryLogin(applicationContext, true)
            } catch(e: IOException) {
                viewModel.setUser(null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}