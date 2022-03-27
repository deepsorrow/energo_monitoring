package com.example.feature_settings.domain

import android.content.Context

object SharedPreferencesManager {
    fun getUsername(context: Context): String? {
        val mPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return mPrefs.getString("username", "")
    }

//    fun getLastClientInfosUpdateDate(context: Context): String? {
//        val mPrefs: SharedPreferences =
//            context.getSharedPreferences("data", Context.MODE_PRIVATE)
//        return mPrefs.getString("lastClientInfoUpdateDate", "")
//    }
//
//    fun setLastClientInfosUpdateDate(date: String, context: Context) {
//        val prefsEditor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
//        prefsEditor.putString("lastClientInfoUpdateDate", date)
//        prefsEditor.apply()
//    }

    fun clearUsername(context: Context) {
        val prefsEditor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        prefsEditor.putString("username", "")
        prefsEditor.apply()
    }

    fun saveUsername(context: Context, username: String?) {
        val prefsEditor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        prefsEditor.putString("username", username)
        prefsEditor.commit()
    }

    fun saveUserId(context: Context, userId: Int) {
        val prefsEditor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        prefsEditor.putInt("userId", userId)
        prefsEditor.commit()
    }
}