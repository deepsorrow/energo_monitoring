package com.example.energy_monitoring.checks.domain.repo

import androidx.room.Query
import com.example.energy_monitoring.checks.data.CheckLengthResult
import com.example.energy_monitoring.checks.data.files.ProjectFile
import com.example.energy_monitoring.checks.data.api.ClientInfo
import com.example.energy_monitoring.checks.data.api.OrganizationInfo
import com.example.energy_monitoring.checks.data.api.ProjectDescription
import com.example.energy_monitoring.checks.data.db.OtherInfo
import com.example.energy_monitoring.checks.data.db.ResultDataDAO
import com.example.energy_monitoring.checks.data.devices.*
import com.example.energy_monitoring.checks.data.files.CheckLengthPhotoFile
import com.example.energy_monitoring.checks.data.files.FinalPhotoFile
import com.example.energy_monitoring.checks.data.files.FinalPhotoType
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ResultDataRepository @Inject constructor(
    private val dao: ResultDataDAO
) {
    fun getDeviceCounters(dataId: Int): List<DeviceCounter>? = dao.getDeviceCounters(dataId)

    fun getDeviceTemperatureCounter(dataId: Int): List<DeviceTemperatureCounter>? = dao.getDeviceTemperatureCounter(dataId)

    fun getDeviceFlowTransducers(dataId: Int): List<DeviceFlowTransducer>? = dao.getDeviceFlowTransducers(dataId)

    fun getDeviceTemperatureTransducers(dataId: Int): List<DeviceTemperatureTransducer>? = dao.getDeviceTemperatureTransducers(dataId)

    fun getDevicePressureTransducers(dataId: Int): List<DevicePressureTransducer>? = dao.getDevicePressureTransducers(dataId)

    fun getFlowTransducerLengths(dataId: Int): List<CheckLengthResult>? {
        val lengthResults = dao.getFlowTransducerLengths(dataId)
        lengthResults?.forEach {
            it.photoFiles = dao.getCheckLengthPhotoFiles(dataId, it.id)?.toMutableList() ?: mutableListOf()
        }
        return lengthResults
    }

    fun getClientInfo(dataId: Int): ClientInfo? =
        dao.getClientInfo(dataId)

    fun getClientInfos() = dao.getClientInfos()

    fun getOtherInfo(dataId: Int): OtherInfo? =
        dao.getOtherInfo(dataId)


    fun getProjectDescription(dataId: Int): ProjectDescription? {
        val project = dao.getProjectDescription(dataId)
        if (project != null) {
            project.files = dao.getProjectFiles(dataId)
        }
        return project
    }

    fun getFinalPhotoFiles(dataId: Int, type: FinalPhotoType) =
        dao.getFinalPhotoFiles(dataId, type)

    fun getResultData(dataId: Int) = dao.getData(dataId)

    fun insertDeviceTemperatureCounters(devices: List<DeviceTemperatureCounter>, isInit: Boolean = false) =
        checkNotEmpty(devices) {
            incrementVersionOrGetError(devices[0].dataId, isInit) {
                dao.insertDeviceTemperatureCounters(devices)
            }
        }

    fun insertDeviceFlowTransducers(devices: List<DeviceFlowTransducer>, isInit: Boolean = false) =
        checkNotEmpty(devices) {
            incrementVersionOrGetError(devices[0].dataId, isInit) {
                dao.insertDeviceFlowTransducers(devices)
            }
        }

    fun insertDeviceTemperatureTransducers(devices: List<DeviceTemperatureTransducer>, isInit: Boolean = false) =
        checkNotEmpty(devices) {
            incrementVersionOrGetError(devices[0].dataId, isInit) {
                dao.insertDeviceTemperatureTransducers(devices)
            }
        }

    fun insertDevicePressureTransducers(devices: List<DevicePressureTransducer>, isInit: Boolean = false) =
        checkNotEmpty(devices) {
            incrementVersionOrGetError(devices[0].dataId, isInit) {
                dao.insertDevicePressureTransducers(devices)
            }
        }

    fun insertDeviceCounters(devices: List<DeviceCounter>, isInit: Boolean = false) =
        checkNotEmpty(devices) {
            incrementVersionOrGetError(devices[0].dataId, isInit) {
                dao.insertDeviceCounters(devices)
            }
        }

    fun insertFlowTransducerCheckLengthResults(results: List<CheckLengthResult>, isInit: Boolean = false) =
        checkNotEmpty(results) {
            incrementVersionOrGetError(results[0].dataId, isInit) {
                dao.insertFlowTransducerCheckLengthResults(results)
            }
        }

    fun insertCheckLengthPhotoFile(file: CheckLengthPhotoFile, isInit: Boolean = false) =
        incrementVersionOrGetError(file.dataId, isInit) {
            dao.insertCheckLengthPhotoFile(file)
        }

    fun insertProjectDescription(projectDescription: ProjectDescription, isInit: Boolean = false): Result<String> =
        incrementVersionOrGetError(projectDescription.dataId, isInit) {
            projectDescription.files.forEach {
                it.dataId = projectDescription.dataId
                it.projectId = projectDescription.id
            }
            dao.insertProjectFiles(projectDescription.files)
            dao.insertProjectDescription(projectDescription)
        }

    fun insertOrganizationInfo(organizationInfo: OrganizationInfo, isInit: Boolean = false) {
        incrementVersionOrGetError(organizationInfo.dataId, isInit) {
            dao.insertOrganizationInfo(organizationInfo)
        }
    }

    fun insertClientInfo(clientInfo: ClientInfo, isInit: Boolean = false): Result<String> =
        incrementVersionOrGetError(clientInfo.dataId, isInit) {
            dao.insertClientInfo(clientInfo)
        }

    fun insertOtherInfo(otherInfo: OtherInfo, isInit: Boolean = false) {
        incrementVersionOrGetError(otherInfo.dataId, isInit) {
            dao.insertOtherInfo(otherInfo)
        }
    }

    fun insertProjectFile(file: ProjectFile) =
        incrementVersionOrGetError(file.dataId, false) {
            dao.insertProjectFile(file)
        }

    fun insertFinalPhotoFile(file: FinalPhotoFile) =
        incrementVersionOrGetError(file.dataId, false) {
            dao.insertFinalPhotoFile(file)
        }

    fun deleteProjectFiles(dataId: Int) = dao.deleteProjectFiles(dataId)

    fun deleteCheckLengthPhotoFiles(dataId: Int) = dao.deleteCheckLengthPhotoFiles(dataId)

    fun deleteFinalPhotoFiles(dataId: Int) = dao.deleteFinalPhotoFiles(dataId)

    fun deleteClientInfo(dataId: Int) = dao.deleteClientInfo(dataId)

    fun deleteOtherInfo(dataId: Int) = dao.deleteOtherInfo(dataId)
    
    fun deleteProjectDescription(dataId: Int) = dao.deleteProjectDescription(dataId)
    
    fun deleteDeviceTemperatureCounter(dataId: Int) = dao.deleteDeviceTemperatureCounter(dataId)
    
    fun deleteDeviceFlowTransducers(dataId: Int) = dao.deleteDeviceFlowTransducers(dataId)
    
    fun deleteDeviceTemperatureTransducers(dataId: Int) = dao.deleteDeviceTemperatureTransducers(dataId)
    
    fun deleteDevicePressureTransducers(dataId: Int) = dao.deleteDevicePressureTransducers(dataId)
    
    fun deleteDeviceCounters(dataId: Int) = dao.deleteDeviceCounters(dataId)

    private fun checkNotEmpty(list: List<Any>, action: () -> Result<String>) =
        if (list.isNotEmpty()) {
            action()
        } else {
            Result.failure(IllegalArgumentException("Список устройств пустой!"))
        }

    private fun incrementVersionOrGetError(dataId: Int, isInit: Boolean, action: () -> Unit): Result<String> {
        if (isInit) {
            action()
            return Result.success("")
        }

        val incrementResult = incrementVersion(dataId)
        return if (incrementResult.isSuccess) {
            action()
            Result.success("")
        } else {
            Result.failure(incrementResult.exceptionOrNull()!!)
        }
    }

    private fun incrementVersion(dataId: Int): Result<String> {
        val otherInfo = dao.getOtherInfo(dataId)
        return if (otherInfo != null) {
            otherInfo.localVersion += 1
            dao.insertOtherInfo(otherInfo)
            Result.success("")
        } else {
            Result.failure(IllegalArgumentException("Нет данных! Код ошибки 281!"))
        }
    }
}