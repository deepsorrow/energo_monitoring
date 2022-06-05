package com.example.energy_monitoring.checks.ui.vm.hosts

import android.app.Application
import androidx.databinding.ObservableField
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureCounter
import com.example.energy_monitoring.checks.ui.fragments.dialogs.AddNewDeviceDialog
import com.example.energy_monitoring.checks.ui.vm.base.BaseScreenVM
import com.example.energy_monitoring.compose.DeviceType
import javax.inject.Inject

class HostMetricsVM @Inject constructor(application: Application)
    : BaseScreenVM(4, application) {

    var tempCounters = mutableListOf<DeviceTemperatureCounter>()

    var comment = ObservableField("")

    var addNewDevice: (() -> Unit)? = null

    override fun initialize() {
        super.initialize()

        if (!initialized) {
            tempCounters.clear()
            tempCounters = repository.getDeviceTemperatureCounter(dataId).orEmpty().toCollection(tempCounters)
            initialized = true
        }

        comment.set(repository.getOtherInfo(dataId)?.counterCharacteristictsComment)
    }

    fun onCommentChanged() {
        val otherInfo = repository.getOtherInfo(dataId)
        if (otherInfo != null) {
            otherInfo.counterCharacteristictsComment = comment.get().orEmpty()
            repository.insertOtherInfo(otherInfo)
        }
    }

    fun addNewDevice() = addNewDevice?.let { it() }

}