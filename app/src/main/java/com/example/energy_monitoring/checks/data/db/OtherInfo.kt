package com.example.energy_monitoring.checks.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore

@Entity
class OtherInfo {
    
    @PrimaryKey(autoGenerate = true)
    var id = 0
    
    var dataId = 0
    
    var clientId = 0
    
    var organizationId = 0
    
    var projectId = 0
    
    var userId = 0
    
    var completedScreens = 0
    
    var currentScreen = 0
    
    var localVersion = 0
    
    var cloudVersion = 0
    
    var completed = false
    
    var lightIsOk: Boolean? = null
    
    var sanPinIsOk: Boolean? = null
    
    var generalInspectionComment: String = ""
    
    var counterCharacteristicts: String = ""
    
    var counterCharacteristictsComment: String = ""

    @Ignore
    var finalPhotosGeneral: String = ""

    @Ignore
    var finalPhotosDevices: String = ""

    @Ignore
    var finalPhotosSeals: String = ""

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