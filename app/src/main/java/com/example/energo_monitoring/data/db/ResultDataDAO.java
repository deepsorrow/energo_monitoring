package com.example.energo_monitoring.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.energo_monitoring.data.FlowTransducerCheckLengthResult;
import com.example.energo_monitoring.data.api.ClientInfo;
import com.example.energo_monitoring.data.api.OrganizationInfo;
import com.example.energo_monitoring.data.api.ProjectDescription;
import com.example.energo_monitoring.data.devices.DeviceCounter;
import com.example.energo_monitoring.data.devices.DeviceFlowTransducer;
import com.example.energo_monitoring.data.devices.DevicePressureTransducer;
import com.example.energo_monitoring.data.devices.DeviceTemperatureCounter;
import com.example.energo_monitoring.data.devices.DeviceTemperatureTransducer;

import java.util.List;

@Dao
public abstract class ResultDataDAO {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public abstract void insertData(ClientInfo clientInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrganizationInfo(OrganizationInfo organizationInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertProjectDescription(ProjectDescription projectDescription);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertClientInfo(ClientInfo clientInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOtherInfo(OtherInfo otherInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertDeviceTemperatureCounter(List<DeviceTemperatureCounter> devices);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertDeviceFlowTransducers(List<DeviceFlowTransducer> devices);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertDeviceTemperatureTransducers(List<DeviceTemperatureTransducer> devices);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertDevicePressureTransducers(List<DevicePressureTransducer> devices);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertDeviceCounters(List<DeviceCounter> devices);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertFlowTransducerCheckLengthResults(List<FlowTransducerCheckLengthResult> results);

    @Query("DELETE FROM OtherInfo WHERE dataId = :dataId")
    public abstract void deleteOtherInfo(int dataId);

    @Query("DELETE FROM ProjectDescription WHERE dataId = :dataId")
    public abstract void deleteProjectDescription(int dataId);

    @Query("DELETE FROM DeviceTemperatureCounter WHERE dataId = :dataId")
    public abstract void deleteDeviceTemperatureCounter(int dataId);

    @Query("DELETE FROM DeviceFlowTransducer WHERE dataId = :dataId")
    public abstract void deleteDeviceFlowTransducers(int dataId);

    @Query("DELETE FROM DeviceTemperatureTransducer WHERE dataId = :dataId")
    public abstract void deleteDeviceTemperatureTransducers(int dataId);

    @Query("DELETE FROM DevicePressureTransducer WHERE dataId = :dataId")
    public abstract void deleteDevicePressureTransducers(int dataId);

    @Query("DELETE FROM DeviceCounter WHERE dataId = :dataId")
    public abstract void deleteDeviceCounters(int dataId);

    @Query("DELETE FROM Clientinfo WHERE dataId = :dataId")
    public abstract void deleteClientInfo(int dataId);

    @Query("SELECT * FROM ProjectDescription WHERE dataId = :dataId")
    public abstract ProjectDescription getProjectDescription(int dataId);

    @Query("SELECT * FROM DevicePressureTransducer WHERE dataId = :dataId")
    public abstract List<DeviceTemperatureCounter> getDeviceTemperatureCounter(int dataId);

    @Query("SELECT * FROM DeviceFlowTransducer WHERE dataId = :dataId")
    public abstract List<DeviceFlowTransducer> getDeviceFlowTransducers(int dataId);

    @Query("SELECT * FROM DeviceTemperatureTransducer WHERE dataId = :dataId")
    public abstract List<DeviceTemperatureTransducer> getDeviceTemperatureTransducers(int dataId);

    @Query("SELECT * FROM DevicePressureTransducer WHERE dataId = :dataId")
    public abstract List<DevicePressureTransducer> getDevicePressureTransducers(int dataId);

    @Query("SELECT * FROM OrganizationInfo WHERE dataId = :dataId")
    public abstract OrganizationInfo getOrganizationInfo(int dataId);

    @Query("SELECT * FROM ClientInfo WHERE dataId = :dataId")
    public abstract ClientInfo getClientInfo(int dataId);

    @Query("SELECT * FROM OtherInfo WHERE dataId = :dataId")
    public abstract OtherInfo getOtherInfo(int dataId);

    @Transaction
    @Query("SELECT * FROM OtherInfo WHERE dataId = :id")
    public abstract ResultData getData(int id);
}
