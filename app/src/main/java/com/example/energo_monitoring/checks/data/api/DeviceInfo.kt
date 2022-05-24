package com.example.energo_monitoring.checks.data.api

import com.example.energo_monitoring.checks.data.devices.Field

open class DeviceInfo(var name: Field<String>, var typeId: Int) {
    var deviceNumber: Field<String>? = null
    var lastCheckDate: Field<String>? = null
}