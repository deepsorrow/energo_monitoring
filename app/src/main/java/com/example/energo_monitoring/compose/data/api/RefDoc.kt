package com.example.energo_monitoring.compose.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RefDoc {
    @PrimaryKey
    val id: Int = 0
    val isFolder: Boolean = false
    val title: String = ""

    val localFilePath: String = ""
    val data: ByteArray = byteArrayOf()
}