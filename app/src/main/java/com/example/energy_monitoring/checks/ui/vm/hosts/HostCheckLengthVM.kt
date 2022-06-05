package com.example.energy_monitoring.checks.ui.vm.hosts

import android.app.Application
import com.example.energy_monitoring.checks.data.CheckLengthResult
import com.example.energy_monitoring.checks.data.devices.DeviceFlowTransducer
import com.example.energy_monitoring.checks.ui.vm.base.BaseScreenVM
import javax.inject.Inject
import kotlin.random.Random

class HostCheckLengthVM @Inject constructor(application: Application)
    : BaseScreenVM(3, application) {

    var devices = listOf<DeviceFlowTransducer>()
    var results = mutableListOf<CheckLengthResult>()

    var addNewDevice: (() -> Unit)? = null

    override fun initialize() {
        super.initialize()

        devices = repository.getDeviceFlowTransducers(dataId).orEmpty()
        results = repository.getFlowTransducerLengths(dataId)?.toMutableList() ?: mutableListOf()
    }

    fun getSavedResult(position: Int): CheckLengthResult =
        if (results.count() > position) {
            results[position]
        } else {
            val result = CheckLengthResult()
            result.id = 102 + Random.nextInt(100000)
            result.dataId = dataId
            result
        }

    fun addNewFlowTransducer() = addNewDevice?.let { it() }
}