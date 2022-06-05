package com.example.energy_monitoring.checks.ui.utils

import android.content.Context

object SharedPreferencesManager {
    @JvmStatic
    fun saveUsername(context: Context, username: String?) {
        val prefsEditor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        prefsEditor.putString("username", username)
        prefsEditor.commit()
    }

    @JvmStatic
    fun saveUserId(context: Context, userId: Int) {
        val prefsEditor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        prefsEditor.putInt("userId", userId)
        prefsEditor.commit()
    }

    @JvmStatic
    fun getUsername(context: Context): String? {
        val mPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return mPrefs.getString("username", "")
    }

    fun getUserId(context: Context): Int {
        val mPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return mPrefs.getInt("userId", 0)
    }
}