package com.example.energo_monitoring.checks.data.devices

import androidx.room.Entity
import com.example.energo_monitoring.checks.data.api.DeviceInfo
import androidx.room.PrimaryKey

@Entity
class DevicePressureTransducer : DeviceInfo(Field(""), 0) {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @JvmField
    var dataId = 0
    var installationPlace = Field("")
    var manufacturer = Field("")
    var values = Field("")
    var sensorRange = Field("")
    var comment = Field("")

//    val filledState: Int
//        get() = if (!(installationPlace == null || installationPlace!!.isEmpty())
//            && !(manufacturer == null || manufacturer!!.isEmpty())
//            && !(sensorRange == null || sensorRange!!.isEmpty())
//            && !(values == null || values!!.isEmpty())
//        ) 2 else if (!(installationPlace == null || installationPlace!!.isEmpty())
//            || !(manufacturer == null || manufacturer!!.isEmpty())
//            || !(sensorRange == null || sensorRange!!.isEmpty())
//            || !(values == null || values!!.isEmpty())
//        ) 1 else 0
}