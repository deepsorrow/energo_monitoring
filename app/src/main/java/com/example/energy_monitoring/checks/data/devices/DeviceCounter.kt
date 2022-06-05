package com.example.energy_monitoring.checks.data.devices

import androidx.room.Entity
import com.example.energy_monitoring.checks.data.api.DeviceInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class DeviceCounter : DeviceInfo() {
    
    @PrimaryKey(autoGenerate = true)
    override var id = 0

    var diameter = Field("")
    var impulseWeight = Field("")
    var volume = ""
}