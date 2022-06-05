package com.example.energy_monitoring.checks.data.files

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.energy_monitoring.checks.data.files.base.BaseFile

@Entity
class FinalPhotoFile : BaseFile() {
    @PrimaryKey
    var id: Int = 0
    var type: FinalPhotoType = FinalPhotoType.Other

    @Ignore
    var bitmap: Bitmap? = null
    @Ignore
    var dataBase64: String = ""
}