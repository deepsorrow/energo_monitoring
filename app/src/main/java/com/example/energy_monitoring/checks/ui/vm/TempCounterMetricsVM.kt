package com.example.energy_monitoring.checks.ui.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.energy_monitoring.checks.data.TempParameter
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureCounter
import com.example.energy_monitoring.checks.domain.repo.ResultDataRepository
import javax.inject.Inject

class TempCounterMetricsVM @Inject constructor(application: Application)
    : ViewModel() {

    var initizaled = false

    @Inject
    lateinit var repository: ResultDataRepository

    lateinit var device: DeviceTemperatureCounter
    var parameters = mutableListOf<TempParameter>()

    fun initialize() {
        parameters.clear()
        if (device.parameters.isEmpty()) {
            parameters.add(TempParameter("Qo", "Гкал"))
            parameters.add(TempParameter("Qгвс", "Гкал"))
            parameters.add(TempParameter("М1", "т"))
            parameters.add(TempParameter("М2", "т"))
            parameters.add(TempParameter("М3", "т"))
            parameters.add(TempParameter("М4", "т"))
            parameters.add(TempParameter("t1", "°C"))
            parameters.add(TempParameter("t2", "°C"))
            parameters.add(TempParameter("t3", "°C"))
            parameters.add(TempParameter("t4", "°C"))
            parameters.add(TempParameter("G1", "м³(т)/ч"))
            parameters.add(TempParameter("G2", "м³(т)/ч"))
            parameters.add(TempParameter("G3", "м³(т)/ч"))
            parameters.add(TempParameter("G4", "м³(т)/ч"))
            parameters.add(TempParameter("P1", "кгс/см²"))
            parameters.add(TempParameter("P2", "кгс/см²"))
            parameters.add(TempParameter("P3", "кгс/см²"))
            parameters.add(TempParameter("P4", "кгс/см²"))
            parameters.add(TempParameter("tи", "ч(сут)"))
            parameters.add(TempParameter("tо", "ч(сут)"))
            parameters.add(TempParameter("V1", "м³"))
            parameters.add(TempParameter("V2", "м³"))
        } else {
            parameters = device.parameters
        }
    }

    fun saveParameters(params: List<TempParameter>) {
        device.parameters = params.toMutableList()
        if (initizaled) {
            repository.insertDeviceTemperatureCounters(listOf(device))
        }
    }
}