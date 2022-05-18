package com.example.energo_monitoring.checks.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class ProjectFile {
    @PrimaryKey
    var id: Int = 0
    var projectId: Int = 0
    var dataId: Int = 0
    var title: String = ""

    @Ignore
    var dataBase64: String = ""
    var extension: String = ""
    var path: String = ""
}
