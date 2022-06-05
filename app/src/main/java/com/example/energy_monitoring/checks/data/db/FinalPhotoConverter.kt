package com.example.energy_monitoring.checks.data.db

import androidx.room.TypeConverter
import com.example.energy_monitoring.checks.data.files.FinalPhotoFile
import com.google.gson.Gson

class FinalPhotoConverter {
    @TypeConverter
    fun fromJsonToFinalPhotos(value: String): List<FinalPhotoFile> {
        return Gson().fromJson(value, Array<FinalPhotoFile>::class.java).toList()
    }

    @TypeConverter
    fun fromFinalPhotosToJson(params: List<FinalPhotoFile>): String {
        val gson = Gson()
        return gson.toJson(params)
    }
}