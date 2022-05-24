package com.example.energo_monitoring.checks.ui.viewmodel.devices

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.checks.data.devices.DevicePressureTransducer
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils

class PressureTransducerVM : ViewModel() {

    var deviceName = ObservableField("")
    var deviceNumber = ObservableField("")
    var installationPlace = ObservableField("")
    var manufacturer = ObservableField("")
    var sensorRange = ObservableField("")
    var lastCheckDate = ObservableField("")
    var values = ObservableField("")
    var comment = ObservableField("")

    lateinit var device: DevicePressureTransducer

    fun initialize(device: DevicePressureTransducer) {
        this.device = device

        deviceName.set(device.deviceName.value)
        deviceNumber.set(device.deviceNumber.value)
        installationPlace.set(device.installationPlace.value)
        manufacturer.set(device.manufacturer.value)
        sensorRange.set(device.sensorRange.value)
        lastCheckDate.set(device.lastCheckDate.value)
        values.set(device.values.value)
        comment.set(device.comment.value)
    }

    fun onDeviceNameChanged() {

    }

    fun onDeviceNumberChanged() {

    }

    fun onInstallationPlaceChanged() {

    }

    fun onManufacturerChanged() {

    }

    fun onSensorRangeChanged() {
        device.sensorRange = s.toString()
    }

    fun onLastDateChanged() {

    }

    fun onValuesChanged() {

    }

    fun onCommentChanged() {

    }
}