package com.example.energo_monitoring.checks.data.devices

import androidx.room.Entity
import com.example.energo_monitoring.checks.data.api.DeviceInfo
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class DeviceFlowTransducer : DeviceInfo, Serializable {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @JvmField
    var dataId = 0
    var installationPlace = Field("")
    var manufacturer = Field("")
    var diameter = Field("")
    var impulseWeight = Field("")
    var values = Field("")
    var comment = Field("")

    constructor() : super( Field(""), 0) {}
    constructor(name: Field<String>, typeId: Int) : super(name, typeId) {}

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