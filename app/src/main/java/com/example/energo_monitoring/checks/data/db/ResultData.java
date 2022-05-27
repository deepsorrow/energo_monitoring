package com.example.energo_monitoring.checks.data.db;

import androidx.room.Embedded;
import androidx.room.Relation;


import com.example.energo_monitoring.checks.data.CheckLengthResult;
import com.example.energo_monitoring.checks.data.api.ClientInfo;
import com.example.energo_monitoring.checks.data.api.OrganizationInfo;
import com.example.energo_monitoring.checks.data.api.ProjectDescription;
import com.example.energo_monitoring.checks.data.devices.DeviceCounter;
import com.example.energo_monitoring.checks.data.devices.DeviceFlowTransducer;
import com.example.energo_monitoring.checks.data.devices.DevicePressureTransducer;
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureCounter;
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureTransducer;

import java.util.List;

public class ResultData {

    //@PrimaryKey
    @Embedded
    public OtherInfo otherInfo;

    @Relation(entityColumn = "dataId", parentColumn = "dataId")
    public ClientInfo clientInfo;
    @Relation(entityColumn = "dataId", parentColumn = "dataId")
    public OrganizationInfo organizationInfo;
    @Relation(entityColumn = "dataId", parentColumn = "dataId")
    public ProjectDescription project;
    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DeviceTemperatureCounter.class)
    public List<DeviceTemperatureCounter> deviceTemperatureCounters;
    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DeviceFlowTransducer.class)
    public List<DeviceFlowTransducer> deviceFlowTransducers;
    @Relation(entityColumn = "dataId", parentColumn = "dataId")
    public List<CheckLengthResult> flowTransducerLengths;
    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DeviceTemperatureTransducer.class)
    public List<DeviceTemperatureTransducer> deviceTemperatureTransducers;
    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DevicePressureTransducer.class)
    public List<DevicePressureTransducer> devicePressureTransducers;
    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DeviceCounter.class)
    public List<DeviceCounter> deviceCounters;

    public ResultData() {
    }

    public ResultData(int dataId, String projectPhotoBase64) {
        this.clientInfo.dataId = dataId;
        this.projectPhotoBase64 = projectPhotoBase64;
    }

    public String projectPhotoBase64;

}
