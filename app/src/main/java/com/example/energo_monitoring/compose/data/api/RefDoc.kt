package com.example.energo_monitoring.compose.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RefDoc() {
    @PrimaryKey
    var id: Int = 0
    var isFolder: Boolean = false
    var parentFolder: String = ""
    var title: String = ""
    var size: String = ""

    var localFilePath: String = ""
    var data: ByteArray = byteArrayOf()

    constructor(title: String) : this() {
        this.title = title
    }

    override fun toString(): String {
        return title
    }

    fun isEmpty() = id == 0 && title == ""
}