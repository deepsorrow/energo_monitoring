package com.example.energy_monitoring.checks.ui.vm.devices

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import com.example.energy_monitoring.checks.data.devices.DevicePressureTransducer
import com.example.energy_monitoring.checks.ui.vm.devices.base.BaseDeviceVM
import javax.inject.Inject

class PressureTransducerVM @Inject constructor(
    application: Application
) : BaseDeviceVM<DevicePressureTransducer>(application) {

    var installationPlace = ObservableField("")
    var manufacturer = ObservableField("")
    var sensorRange = ObservableField("")
    var lastCheckDate = ObservableField("")
    var values = ObservableField("")
    var comment = ObservableField("")

    override fun initialize(deviceId: Int) {
        super.initialize(deviceId)

        installationPlace.set(device.installationPlace.value)
        manufacturer.set(device.manufacturer.value)
        sensorRange.set(device.sensorRange.value)
        lastCheckDate.set(device.lastCheckDate.value)
        values.set(device.values.value)
        comment.set(device.comment.value)
    }

    fun onDeviceNameChanged() =
        saveField {
            device.deviceName.value = deviceName.get().orEmpty()
        }

    fun onDeviceNumberChanged() =
        saveField {
            device.deviceNumber.value = deviceNumber.get().orEmpty()
        }

    fun onInstallationPlaceChanged() =
        saveField {
            device.installationPlace.value = installationPlace.get().orEmpty()
        }

    fun onManufacturerChanged() =
        saveField {
            device.manufacturer.value = manufacturer.get().orEmpty()
        }

    fun onSensorRangeChanged() =
        saveField {
            device.sensorRange.value = sensorRange.get().orEmpty()
        }

    fun onLastDateChanged() =
        saveField {
            device.lastCheckDate.value = lastCheckDate.get().orEmpty()
        }

    fun onValuesChanged() =
        saveField {
            device.values.value = values.get().orEmpty()
        }

    fun onCommentChanged() =
        saveField {
            device.comment.value = comment.get().orEmpty()
        }

    private fun saveField(saveToDevice: () -> Unit) {
        saveToDevice()

        if (initialized) {
            val result = repository.insertDevicePressureTransducers(listOf(device), false)
            if (result.isFailure) {
                Toast.makeText(
                    context,
                    "Не удалось сохранить! Код ошибки: 176. ${result.exceptionOrNull()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}