package com.example.energo_monitoring.checks.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore

@Entity
class OtherInfo {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @JvmField
    var dataId = 0
    @JvmField
    var clientId = 0
    @JvmField
    var organizationId = 0
    @JvmField
    var projectId = 0
    @JvmField
    var userId = 0
    @JvmField
    var completedScreens = 0
    @JvmField
    var currentScreen = 0
    @JvmField
    var localVersion = 0
    @JvmField
    var cloudVersion = 0
    @JvmField
    var completed = false
    @JvmField
    var lightIsOk: Boolean? = null
    @JvmField
    var sanPinIsOk: Boolean? = null
    @JvmField
    var generalInspectionComment: String = ""
    @JvmField
    var counterCharacteristicts: String = ""
    @JvmField
    var counterCharacteristictsComment: String = ""

    @Ignore
    var finalPhotosGeneral: String = ""

    @Ignore
    var finalPhotosDevices: String = ""

    @Ignore
    var finalPhotosSeals: String = ""
    @JvmField
    var finalPhotosGeneralPath: String = ""
    @JvmField
    var finalPhotosDevicesPath: String = ""
    @JvmField
    var finalPhotosSealsPath: String = ""

    constructor() {}

    @Ignore
    constructor(dataId: Int) {
        this.dataId = dataId
    }

    @Ignore
    constructor(dataId: Int, lightIsOk: Boolean?, sanPinIsOk: Boolean?, generalInspectionComment: String) {
        this.dataId = dataId
        this.lightIsOk = lightIsOk
        this.sanPinIsOk = sanPinIsOk
        this.generalInspectionComment = generalInspectionComment
    }
}