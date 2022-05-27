package com.example.energo_monitoring.checks.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.energo_monitoring.checks.data.devices.DeviceFlowTransducer
import com.example.energo_monitoring.checks.data.files.CheckLengthPhotoFile

@Entity
class CheckLengthResult {
    @JvmField
    @PrimaryKey
    var id = 0
    @JvmField
    var dataId = 0
    var lengthBefore = ""
    var lengthAfter = ""
    var comment = ""

    @Ignore
    var photosBase64: String? = null

    @Ignore // будет заполнено вручную
    var photoFiles = mutableListOf<CheckLengthPhotoFile>()

    @Ignore
    var device: DeviceFlowTransducer? = null

    constructor()

    constructor(id: Int, device: DeviceFlowTransducer?, dataId: Int) {
        this.id = id
        this.device = device
        this.dataId = dataId
    }

    val deviceName: String
        get() = device!!.deviceName.value
}