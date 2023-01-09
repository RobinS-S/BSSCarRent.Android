package com.bss.carrent.misc

import android.content.Context
import okhttp3.Credentials

class AuthHelper(context: Context) {
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

    fun getAuthorizationHeader(): String? {
        if(this.areCredentialsFilled()) {
            return Credentials.basic(this.getUsername()!!, getPassword()!!)
        }
        return null;
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