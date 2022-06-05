package com.example.energy_monitoring.checks.ui.vm.devices.base

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.example.energy_monitoring.checks.data.api.DeviceInfo
import com.example.energy_monitoring.checks.di.modules.VM_DEVICE_INSPECTION_VM
import com.example.energy_monitoring.checks.domain.repo.ResultDataRepository
import com.example.energy_monitoring.checks.ui.vm.hosts.HostDevicesVM
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Named

abstract class BaseDeviceVM<T : DeviceInfo>(application: Application)
    : AndroidViewModel(application) {

    @Inject
    @Named(VM_DEVICE_INSPECTION_VM)
    lateinit var parentViewModel: Lazy<HostDevicesVM>

    @Inject
    lateinit var repository: ResultDataRepository

    val context: Context
        get() = getApplication()

    lateinit var device: T

    var deviceName = ObservableField("")
    var deviceNumber = ObservableField("")

    var initialized = false

    open fun initialize(deviceId: Int) {
        device = parentViewModel.get().devices[deviceId] as T

        deviceName.set(device.deviceName.getValue())
        deviceNumber.set(device.deviceNumber.getValue())
    }
}