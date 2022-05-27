package com.example.energo_monitoring.checks.data.files.base


abstract class BaseFile {
    var dataId: Int = 0
    var title: String = ""

    var extension: String = ""
    var path: String = ""
}