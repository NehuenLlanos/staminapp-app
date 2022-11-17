package com.staminapp.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun loadAuthToken() : String? {
        return preferences.getString(AUTH_TOKEN, null)
    }

    fun saveAuthToken(token: String) {
        // Devuelve un editor
        val editor = preferences.edit()

        editor.putString(AUTH_TOKEN, token)
        // Importantisimo sino no se aplican los cambios
        editor.apply()
    }

    fun removeAuthToken() {
        val editor = preferences.edit()
        editor.remove(AUTH_TOKEN)
        editor.apply()
    }

    fun getShowRecent(): Boolean {
        return preferences.getBoolean(SHOW_RECENT, true)
    }

    fun setShowRecent(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(SHOW_RECENT, value)
        editor.apply()
    }

    fun getDropdownsOpen(): Boolean {
        return preferences.getBoolean(DROPDOWNS_OPEN, true)
    }

    fun setDropdownsOpen(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(DROPDOWNS_OPEN, value)
        editor.apply()
    }

    // 0 -> Immersive ; 1 -> Detailed
    fun getExecutionMode(): Int {
        return preferences.getInt(EXECUTION_MODE, 0)
    }

    fun setExecutionMode(value: Int) {
        val editor = preferences.edit()
        editor.putInt(EXECUTION_MODE, value)
        editor.apply()
    }

    companion object {
        const val PREFERENCES_NAME = "preferences"
        const val AUTH_TOKEN = "auth_token"
        const val SHOW_RECENT = "show_recent"
        const val DROPDOWNS_OPEN = "dropdowns_open"
        const val EXECUTION_MODE = "execution_mode"
    }
}