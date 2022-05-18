package com.example.energo_monitoring.checks.data.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class OtherInfo {

    @PrimaryKey (autoGenerate = true)
    public int id;
    public int dataId;
    public int clientId;
    public int organizationId;
    public int projectId;
    public int userId;
    public int completedScreens;
    public int currentScreen;
    public int localVersion;
    public int cloudVersion;
    public boolean completed;

    public Boolean lightIsOk;
    public Boolean sanPinIsOk;
    public String generalInspectionComment;
    public String counterCharacteristicts;
    public String counterCharacteristictsComment;
    @Ignore
    public String finalPhotosGeneral;
    @Ignore
    public String finalPhotosDevices;
    @Ignore
    public String finalPhotosSeals;
    public String finalPhotosGeneralPath;
    public String finalPhotosDevicesPath;
    public String finalPhotosSealsPath;

    public OtherInfo() {
    }

    @Ignore
    public OtherInfo(int dataId) {
        this.dataId = dataId;
    }

    @Ignore
    public OtherInfo(int dataId, Boolean lightIsOk, Boolean sanPinIsOk, String generalInspectionComment) {
        this.dataId = dataId;
        this.lightIsOk = lightIsOk;
        this.sanPinIsOk = sanPinIsOk;
        this.generalInspectionComment = generalInspectionComment;
    }
}