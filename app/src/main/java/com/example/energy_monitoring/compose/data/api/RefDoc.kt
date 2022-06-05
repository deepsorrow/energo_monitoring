package com.example.energy_monitoring.compose.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RefDoc() {
    @PrimaryKey
    var id: Int = 0
    var parentId: Int = 0
    var isFolder: Boolean = false
    var parentFolderName: String = ""
    var title: String = ""
    var size: String = ""

    var localFilePath: String? = ""
    var data: ByteArray = byteArrayOf()
    var dataString: String? = ""

    constructor(title: String, isFolder: Boolean = false) : this() {
        this.title = title
        this.isFolder = isFolder
    }

    override fun toString(): String {
        return title
    }

    fun isEmpty() = id == 0 && title == ""
}