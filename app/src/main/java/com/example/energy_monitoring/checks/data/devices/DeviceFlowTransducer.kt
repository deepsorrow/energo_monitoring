package com.example.energy_monitoring.checks.data.devices

import androidx.room.Entity
import com.example.energy_monitoring.checks.data.api.DeviceInfo
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class DeviceFlowTransducer : DeviceInfo(), Serializable {

    @PrimaryKey(autoGenerate = true)
    override var id = 0

    var installationPlace = Field("")
    var manufacturer = Field("")
    var diameter = Field("")
    var impulseWeight = Field("")
    var values = Field("")
    var comment = Field("")

//    val filledState: Int
//        get() = if (!(installationPlace == null || installationPlace.isEmpty())
//            && !(manufacturer == null || manufacturer.isEmpty())
//            && !(diameter == null || diameter.isEmpty())
//            && !(impulseWeight == null || impulseWeight.isEmpty())
//            && !(values == null || values.isEmpty())
//        ) 2 else if (!(installationPlace == null || installationPlace.isEmpty())
//            || !(manufacturer == null || manufacturer.isEmpty())
//            || !(diameter == null || diameter.isEmpty())
//            || !(impulseWeight == null || impulseWeight.isEmpty())
//            || !(values == null || values.isEmpty())
//        ) 1 else 0
}