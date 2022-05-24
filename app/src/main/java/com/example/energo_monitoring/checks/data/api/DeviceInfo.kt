package com.example.energo_monitoring.checks.data.api

import com.example.energo_monitoring.checks.data.devices.Field

open class DeviceInfo() {
    var deviceName = Field("")
    var deviceNumber = Field("")
    var typeId = 0
    var lastCheckDate = Field("")
}