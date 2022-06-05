package com.example.energy_monitoring.checks.data.db

import androidx.room.TypeConverter
import com.example.energy_monitoring.checks.data.TempParameter
import com.google.gson.Gson

class TempParameterConverter {
    @TypeConverter
    fun fromJsonToTempParameter(value: String): List<TempParameter> {
        return Gson().fromJson(value, Array<TempParameter>::class.java).toList()
    }

    @TypeConverter
    fun fromTempParameterToJson(params: List<TempParameter>): String {
        val gson = Gson()
        return gson.toJson(params)
    }
}