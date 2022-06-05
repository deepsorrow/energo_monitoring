package com.example.energy_monitoring.checks.data.db

import androidx.room.Embedded
import androidx.room.Relation
import com.example.energy_monitoring.checks.data.api.ClientInfo
import com.example.energy_monitoring.checks.data.api.OrganizationInfo
import com.example.energy_monitoring.checks.data.api.ProjectDescription
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureCounter
import com.example.energy_monitoring.checks.data.devices.DeviceFlowTransducer
import com.example.energy_monitoring.checks.data.CheckLengthResult
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureTransducer
import com.example.energy_monitoring.checks.data.devices.DevicePressureTransducer
import com.example.energy_monitoring.checks.data.devices.DeviceCounter
import com.example.energy_monitoring.checks.data.files.FinalPhotoFile

class ResultData {
    @Embedded
    var otherInfo: OtherInfo? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId")
    var clientInfo: ClientInfo? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId")
    var organizationInfo: OrganizationInfo? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId")
    var project: ProjectDescription? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DeviceTemperatureCounter::class)
    var deviceTemperatureCounters: List<DeviceTemperatureCounter>? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DeviceFlowTransducer::class)
    var deviceFlowTransducers: List<DeviceFlowTransducer>? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId")
    var flowTransducerLengths: List<CheckLengthResult>? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DeviceTemperatureTransducer::class)
    var deviceTemperatureTransducers: List<DeviceTemperatureTransducer>? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DevicePressureTransducer::class)
    var devicePressureTransducers: List<DevicePressureTransducer>? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = DeviceCounter::class)
    var deviceCounters: List<DeviceCounter>? = null

    @Relation(entityColumn = "dataId", parentColumn = "dataId", entity = FinalPhotoFile::class)
    var finalPhotos = mutableListOf<FinalPhotoFile>()
}