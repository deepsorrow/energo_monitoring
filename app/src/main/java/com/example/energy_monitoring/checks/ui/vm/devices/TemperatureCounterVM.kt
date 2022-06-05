package com.example.energy_monitoring.checks.ui.vm.devices

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureCounter
import com.example.energy_monitoring.checks.ui.vm.devices.base.BaseDeviceVM
import javax.inject.Inject

class TemperatureCounterVM @Inject constructor(
    application: Application
) : BaseDeviceVM<DeviceTemperatureCounter>(application) {

    var unitSystem = ObservableField("")
    var modification = ObservableField("")
    var lastCheckDate = ObservableField("")
    var interval = ObservableField("")
    var comment = ObservableField("")

    override fun initialize(deviceId: Int) {
        super.initialize(deviceId)

        unitSystem.set(device.unitSystem.value)
        modification.set(device.modification.value)
        lastCheckDate.set(device.lastCheckDate.value)
        interval.set(device.interval.value)
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

    fun onUnitSystemChanged() =
        saveField {
            device.unitSystem.value = unitSystem.get().orEmpty()
        }

    fun onModificationChanged() =
        saveField {
            device.modification.value = modification.get().orEmpty()
        }

    fun onLastDateChanged() =
        saveField {
            device.lastCheckDate.value = lastCheckDate.get().orEmpty()
        }

    fun onIntervalChanged() =
        saveField {
            device.interval.value = interval.get().orEmpty()
        }

    fun onCommentChanged() =
        saveField {
            device.comment.value = comment.get().orEmpty()
        }

    private fun saveField(saveToDevice: () -> Unit) {
        saveToDevice()

        if (initialized) {
            val result = repository.insertDeviceTemperatureCounters(listOf(device), false)
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