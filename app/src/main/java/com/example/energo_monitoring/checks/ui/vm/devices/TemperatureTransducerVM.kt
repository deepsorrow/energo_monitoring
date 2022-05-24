package com.example.energo_monitoring.checks.ui.vm.devices

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureTransducer
import com.example.energo_monitoring.checks.ui.vm.devices.base.BaseDeviceVM
import javax.inject.Inject

class TemperatureTransducerVM @Inject constructor(
    application: Application
) : BaseDeviceVM<DeviceTemperatureTransducer>(application) {

    var installationPlace = ObservableField("")
    var length = ObservableField("")
    var lastCheckDate = ObservableField("")
    var values = ObservableField("")
    var comment = ObservableField("")

    override fun initialize(deviceId: Int) {
        super.initialize(deviceId)

        installationPlace.set(device.installationPlace.value)
        length.set(device.length.value)
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

    fun onLengthChanged() =
        saveField {
            device.length.value = length.get().orEmpty()
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
        val result = repository.insertDeviceTemperatureTransducers(listOf(device), false)
        if (result.isFailure) {
            Toast.makeText(
                context,
                "Не удалось сохранить! Код ошибки: 176. ${result.exceptionOrNull()}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}