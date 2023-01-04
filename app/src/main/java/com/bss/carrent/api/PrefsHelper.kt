package com.bss.carrent.api

import android.content.Context

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

    fun update(username: String, password: String) {
        val preferenceEditor = prefs.edit()
        preferenceEditor.putString("username", username)
        preferenceEditor.putString("password", password)
        preferenceEditor.apply()
    }

    fun reset() {
        val preferenceEditor = prefs.edit()
        preferenceEditor.clear()
        preferenceEditor.apply()
    }
}