package com.example.energo_monitoring.compose.domain

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    fun getUsername(context: Context): String? {
        val mPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return mPrefs.getString("username", "")
    }

    fun getLastClientsInfoUpdateDate(context: Context): String? {
        val mPrefs: SharedPreferences =
            context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return mPrefs.getString("lastClientInfoUpdateDate", "")
    }

    fun setLastClientsInfoUpdateDate(date: String, context: Context) {
        val prefsEditor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        prefsEditor.putString("lastClientInfoUpdateDate", date)
        prefsEditor.apply()
    }

    fun clearUsername(context: Context) {
        val prefsEditor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        prefsEditor.putString("username", "")
        prefsEditor.apply()
    }
}