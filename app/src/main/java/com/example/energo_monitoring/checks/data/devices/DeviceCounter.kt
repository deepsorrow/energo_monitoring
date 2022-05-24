package com.example.energo_monitoring.checks.data.devices

import androidx.room.Entity
import com.example.energo_monitoring.checks.data.api.DeviceInfo
import androidx.room.PrimaryKey

@Entity
class DeviceCounter : DeviceInfo() {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @JvmField
    var dataId = 0
    var diameter = Field("")
    var impulseWeight = Field("")

}