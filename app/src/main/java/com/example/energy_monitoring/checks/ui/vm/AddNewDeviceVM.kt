package com.example.energy_monitoring.checks.ui.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.energy_monitoring.checks.data.api.DeviceInfo
import com.example.energy_monitoring.checks.data.db.ResultDataDatabase
import com.example.energy_monitoring.checks.data.devices.DeviceFlowTransducer
import com.example.energy_monitoring.checks.data.devices.DevicePressureTransducer
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureCounter
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureTransducer
import com.example.energy_monitoring.checks.di.DATA_ID
import com.example.energy_monitoring.checks.domain.repo.ResultDataRepository
import com.example.energy_monitoring.compose.DeviceType
import javax.inject.Inject
import javax.inject.Named

class AddNewDeviceVM @Inject constructor(
    @Named(DATA_ID) val dataId: Int,
    val repository: ResultDataRepository
    )
    : ViewModel() {

    val deviceType = ObservableField("")
    val deviceName = ObservableField("")
    val deviceNumber = ObservableField("")
    val additionalField1 = ObservableField("")
    val additionalField2 = ObservableField("")

    val deviceTypes = DeviceType.values().toList().map { it.title }.filter { it.isNotEmpty() }

    fun onDeviceTypeChanged() {

    }

    fun onDeviceNameChanged() {

    }

    fun onDeviceNumberChanged() {

    }

    fun onAdditionalField1Changed() {

    }

    fun onAdditionalField2Changed() {

    }

    fun saveDevice() {
        when (deviceType.get().orEmpty()) {
            DeviceType.TemperatureCounter.title -> {
                val tempCounter = DeviceTemperatureCounter()
                (tempCounter as DeviceInfo).initDeviceInfoFields()
                repository.insertDeviceTemperatureCounters(listOf(tempCounter))
            }

            DeviceType.FlowTransducer.title -> {
                val flowTransducer = DeviceFlowTransducer().apply {
                    diameter.initialValue = additionalField1.get().orEmpty()
                    impulseWeight.initialValue = additionalField2.get().orEmpty()
                }
                (flowTransducer as DeviceInfo).initDeviceInfoFields()
                repository.insertDeviceFlowTransducers(listOf(flowTransducer))
            }

            DeviceType.TemperatureTransducer.title -> {
                val tempTransducer = DeviceTemperatureTransducer().apply {
                    length.initialValue = additionalField1.get().orEmpty()
                }
                (tempTransducer as DeviceInfo).initDeviceInfoFields()
                repository.insertDeviceTemperatureTransducers(listOf(tempTransducer))
            }

            DeviceType.PressureTransducer.title -> {
                val pressureTransducer = DevicePressureTransducer().apply {
                    sensorRange.initialValue = additionalField1.get().orEmpty()
                }
                (pressureTransducer as DeviceInfo).initDeviceInfoFields()
                repository.insertDevicePressureTransducers(listOf(pressureTransducer))
            }
        }
    }

    private fun DeviceInfo.initDeviceInfoFields() {
        deviceName.initialValue = this@AddNewDeviceVM.deviceName.get().orEmpty()
        deviceNumber.initialValue = this@AddNewDeviceVM.deviceNumber.get().orEmpty()
        typeId = DeviceType.values().find { it.title == deviceType.get().orEmpty() }?.typeId ?: 0
        dataId = this@AddNewDeviceVM.dataId
    }

}