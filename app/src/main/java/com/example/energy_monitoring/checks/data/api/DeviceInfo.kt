package com.example.energy_monitoring.checks.data.api

import com.example.energy_monitoring.checks.data.devices.Field
import com.google.gson.annotations.SerializedName

open class DeviceInfo {
    @SerializedName("id2")
    open var id = 0
    var dataId = 0
    var deviceName = Field("")
    var deviceNumber = Field("")
    var typeId = 0
    var lastCheckDate = Field("")
}