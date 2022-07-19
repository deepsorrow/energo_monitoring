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

    fun hideDataId(context: Context, dataId: Int) {
        val mPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

        val stringSet = mPrefs.getStringSet("hidedDataIds", HashSet())
        val newStringSet = stringSet?.let { HashSet(it) }
        newStringSet?.add(dataId.toString())

        val prefsEditor = mPrefs.edit()
        prefsEditor.putStringSet("hidedDataIds", newStringSet)
        prefsEditor.commit()
    }

    fun getHidedDataIds(context: Context): List<Int> {
        val mPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return mPrefs.getStringSet("hidedDataIds", HashSet())!!.toList().map { it.toInt() }
    }
}