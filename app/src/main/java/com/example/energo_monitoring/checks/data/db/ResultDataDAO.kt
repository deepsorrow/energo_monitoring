package com.example.energo_monitoring.checks.data.db

import androidx.room.*
import com.example.energo_monitoring.checks.data.api.ProjectDescription
import com.example.energo_monitoring.checks.data.api.OrganizationInfo
import com.example.energo_monitoring.checks.data.api.ClientInfo
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureCounter
import com.example.energo_monitoring.checks.data.devices.DeviceFlowTransducer
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureTransducer
import com.example.energo_monitoring.checks.data.devices.DevicePressureTransducer
import com.example.energo_monitoring.checks.data.devices.DeviceCounter
import com.example.energo_monitoring.checks.data.FlowTransducerLength
import com.example.energo_monitoring.checks.data.ProjectFile

@Dao
abstract class ResultDataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrganizationInfo(organizationInfo: OrganizationInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjectDescription(projectDescription: ProjectDescription)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertClientInfo(clientInfo: ClientInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOtherInfo(otherInfo: OtherInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDeviceTemperatureCounters(devices: List<DeviceTemperatureCounter>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDeviceFlowTransducers(devices: List<DeviceFlowTransducer?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDeviceTemperatureTransducers(devices: List<DeviceTemperatureTransducer?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDevicePressureTransducers(devices: List<DevicePressureTransducer?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDeviceCounters(devices: List<DeviceCounter?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFlowTransducerCheckLengthResults(results: List<FlowTransducerLength?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjectFiles(files: List<ProjectFile?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjectFile(file: ProjectFile)

    @Query("DELETE FROM OtherInfo WHERE dataId = :dataId")
    abstract fun deleteOtherInfo(dataId: Int)

    @Query("DELETE FROM ProjectDescription WHERE dataId = :dataId")
    abstract fun deleteProjectDescription(dataId: Int)

    @Query("DELETE FROM DeviceTemperatureCounter WHERE dataId = :dataId")
    abstract fun deleteDeviceTemperatureCounter(dataId: Int)

    @Query("DELETE FROM DeviceFlowTransducer WHERE dataId = :dataId")
    abstract fun deleteDeviceFlowTransducers(dataId: Int)

    @Query("DELETE FROM DeviceTemperatureTransducer WHERE dataId = :dataId")
    abstract fun deleteDeviceTemperatureTransducers(dataId: Int)

    @Query("DELETE FROM DevicePressureTransducer WHERE dataId = :dataId")
    abstract fun deleteDevicePressureTransducers(dataId: Int)

    @Query("DELETE FROM DeviceCounter WHERE dataId = :dataId")
    abstract fun deleteDeviceCounters(dataId: Int)

    @Query("DELETE FROM Clientinfo WHERE dataId = :dataId")
    abstract fun deleteClientInfo(dataId: Int)

    @Query("SELECT * FROM ProjectDescription WHERE dataId = :dataId")
    abstract fun getProjectDescription(dataId: Int): ProjectDescription?

    @Query("SELECT * FROM DeviceCounter WHERE dataId = :dataId")
    abstract fun getDeviceCounters(dataId: Int): List<DeviceCounter>?

    @Query("SELECT * FROM DeviceTemperatureCounter WHERE dataId = :dataId")
    abstract fun getDeviceTemperatureCounter(dataId: Int): List<DeviceTemperatureCounter>?

    @Query("SELECT * FROM DeviceFlowTransducer WHERE dataId = :dataId")
    abstract fun getDeviceFlowTransducers(dataId: Int): List<DeviceFlowTransducer>?

    @Query("SELECT * FROM DeviceTemperatureTransducer WHERE dataId = :dataId")
    abstract fun getDeviceTemperatureTransducers(dataId: Int): List<DeviceTemperatureTransducer>?

    @Query("SELECT * FROM DevicePressureTransducer WHERE dataId = :dataId")
    abstract fun getDevicePressureTransducers(dataId: Int): List<DevicePressureTransducer>?

    @Query("SELECT * FROM OrganizationInfo WHERE dataId = :dataId")
    abstract fun getOrganizationInfo(dataId: Int): OrganizationInfo?

    @Query("SELECT * FROM ClientInfo WHERE dataId = :dataId")
    abstract fun getClientInfo(dataId: Int): ClientInfo?

    @Query("SELECT * FROM OtherInfo WHERE dataId = :dataId")
    abstract fun getOtherInfo(dataId: Int): OtherInfo?

    @Query("SELECT * FROM ProjectFile WHERE dataId = :dataId")
    abstract fun getProjectFiles(dataId: Int): List<ProjectFile?>?

    @Transaction
    @Query("SELECT * FROM OtherInfo WHERE dataId = :id")
    abstract fun getData(id: Int): ResultData?
}