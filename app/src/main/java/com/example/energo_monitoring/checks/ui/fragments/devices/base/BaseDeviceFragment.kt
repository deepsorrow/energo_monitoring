package com.example.energo_monitoring.checks.ui.fragments.devices.base

import com.example.energo_monitoring.checks.di.modules.VM_DEVICE_INSPECTION_VM
import com.example.energo_monitoring.checks.ui.viewmodel.DeviceInspectionVM
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

abstract class BaseDeviceFragment : DaggerFragment() {

    @Inject
    @Named(VM_DEVICE_INSPECTION_VM)
    lateinit var parentViewModel: DeviceInspectionVM

    val disposables = CompositeDisposable()

}