package com.bss.carrent.misc

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import okhttp3.Credentials

class PrefsHelper(context: Context) {
    private val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    fun areCredentialsFilled(): Boolean {
        val username = prefs.getString("username", "")
        val password = prefs.getString("password", "")
        return !username.isNullOrEmpty() && !password.isNullOrEmpty()
    }

    fun getUsername(): String? {
        return prefs.getString("username", "")
    }

    fun getPassword(): String? {
        return prefs.getString("password", "")
    }

    fun getTheme(): String? {
        return prefs.getString("theme", "")
    }

    fun getAuthorizationHeader(): String? {
        if (this.areCredentialsFilled()) {
            return Credentials.basic(this.getUsername()!!, getPassword()!!)
        }
        return null;
    }

    fun updateCredentials(username: String, password: String) {
        val preferenceEditor = prefs.edit()
        preferenceEditor.putString("username", username)
        preferenceEditor.putString("password", password)
        preferenceEditor.apply()
    }

    fun updateTheme(value: String) {
        val preferenceEditor = prefs.edit()
        preferenceEditor.putString("theme", value)
        preferenceEditor.apply()
        loadTheme()
    }

    fun loadTheme() {
        when (getTheme()) {
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            null -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun resetCredentials() {
        val preferenceEditor = prefs.edit()
        preferenceEditor.remove("username")
        preferenceEditor.remove("password")
        preferenceEditor.apply()
    }
}