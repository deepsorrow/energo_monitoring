package com.example.energy_monitoring.checks.data.db

import androidx.room.TypeConverter
import com.example.energy_monitoring.checks.data.devices.Field
import com.google.gson.Gson

class FieldConverter {
    @TypeConverter
    fun fromJsonToField(value: String): Field {
        return Gson().fromJson(value, Field::class.java)
    }

    @TypeConverter
    fun fromFieldToJson(field: Field): String {
        val gson = Gson()
        return gson.toJson(field)
    }
}