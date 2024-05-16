package com.example.mobileappui.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.mobileappui.route.Account

class PreferencesHelper(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "mobile_app_ui_prefs"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
    }

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var authToken: String?
        get() = prefs.getString(KEY_AUTH_TOKEN, null)
        set(value) = prefs.edit().putString(KEY_AUTH_TOKEN, value).apply()

    var userId: Long
        get() = prefs.getLong(KEY_USER_ID, 0)
        set(value) = prefs.edit().putLong(KEY_USER_ID, value).apply()

    fun clear() {
        prefs.edit().clear().apply()
    }
}
