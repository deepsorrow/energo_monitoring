package com.example.energo_monitoring.data.db;

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

    public boolean lightIsOk;
    public boolean sanPinIsOk;
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

    public OtherInfo(int dataId, boolean lightIsOk, boolean sanPinIsOk, String generalInspectionComment) {
        this.dataId = dataId;
        this.lightIsOk = lightIsOk;
        this.sanPinIsOk = sanPinIsOk;
        this.generalInspectionComment = generalInspectionComment;
    }
}