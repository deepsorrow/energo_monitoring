package com.example.energo_monitoring.checks.domain.repo

import com.example.energo_monitoring.checks.data.FlowTransducerLength
import com.example.energo_monitoring.checks.data.ProjectFile
import com.example.energo_monitoring.checks.data.api.ClientInfo
import com.example.energo_monitoring.checks.data.api.OrganizationInfo
import com.example.energo_monitoring.checks.data.api.ProjectDescription
import com.example.energo_monitoring.checks.data.db.OtherInfo
import com.example.energo_monitoring.checks.data.db.ResultDataDAO
import com.example.energo_monitoring.checks.data.devices.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ResultDataRepository @Inject constructor(
    private val dao: ResultDataDAO
) {
    fun insertDeviceTemperatureCounters(devices: List<DeviceTemperatureCounter>, isInit: Boolean = false) =
        incrementVersionOrGetError(devices[0].dataId, isInit) {
            dao.insertDeviceTemperatureCounters(devices)
        }

    fun insertDeviceFlowTransducers(devices: List<DeviceFlowTransducer>, isInit: Boolean = false) =
        incrementVersionOrGetError(devices[0].dataId, isInit) {
            dao.insertDeviceFlowTransducers(devices)
        }

    fun insertDeviceTemperatureTransducers(devices: List<DeviceTemperatureTransducer>, isInit: Boolean = false) =
        incrementVersionOrGetError(devices[0].dataId, isInit) {
            dao.insertDeviceTemperatureTransducers(devices)
        }

    fun insertDevicePressureTransducers(devices: List<DevicePressureTransducer>, isInit: Boolean = false) =
        incrementVersionOrGetError(devices[0].dataId, isInit) {
            dao.insertDevicePressureTransducers(devices)
        }

    fun insertDeviceCounters(devices: List<DeviceCounter>, isInit: Boolean = false) =
        incrementVersionOrGetError(devices[0].dataId, isInit) {
            dao.insertDeviceCounters(devices)
        }

    fun insertFlowTransducerCheckLengthResults(results: List<FlowTransducerLength>, isInit: Boolean = false) =
        incrementVersionOrGetError(results[0].dataId, isInit) {
            dao.insertFlowTransducerCheckLengthResults(results)
        }

    fun insertProjectDescription(
        projectDescription: ProjectDescription,
        isInit: Boolean = false
    ): Result<String> =
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


    fun getClientInfo(dataId: Int): ClientInfo? =
        dao.getClientInfo(dataId)


    fun getOtherInfo(dataId: Int): OtherInfo? =
        dao.getOtherInfo(dataId)


    fun getProjectDescription(dataId: Int): ProjectDescription? {
        val project = dao.getProjectDescription(dataId)
        if (project != null) {
            project.files = dao.getProjectFiles(dataId)
        }
        return project
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