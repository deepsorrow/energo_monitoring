package com.example.energo_monitoring.compose.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RefDoc() {
    @PrimaryKey
    val id: Int = 0
    val isFolder: Boolean = false
    val parentFolder: String = ""
    var title: String = ""
    val size: String = ""

    val localFilePath: String = ""
    val data: ByteArray = byteArrayOf()

    constructor(title: String) : this() {
        this.title = title
    }

    override fun toString(): String {
        return title
    }

    fun isEmpty() = id == 0 && title == ""
}