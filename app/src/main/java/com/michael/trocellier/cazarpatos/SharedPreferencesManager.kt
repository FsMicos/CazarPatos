package com.michael.trocellier.cazarpatos

import android.app.Activity
import android.content.Context

class SharedPreferencesManager(private val activity: Activity) : FileHandler {

    companion object {
        private const val LOGIN_KEY = "user_login_key"
        private const val PASSWORD_KEY = "user_password_key"
    }

    private val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)

    override fun SaveInformation(dataToSave: Pair<String, String>) {
        sharedPref.edit().apply {
            putString(LOGIN_KEY, dataToSave.first)
            putString(PASSWORD_KEY, dataToSave.second)
            apply() // 'apply()' aquí guarda los cambios de forma asíncrona.
        }
    }

    override fun ReadInformation(): Pair<String, String> {
        val email = sharedPref.getString(LOGIN_KEY, "") ?: ""
        val password = sharedPref.getString(PASSWORD_KEY, "") ?: ""
        return (email to password)
    }
}