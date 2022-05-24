package com.example.energo_monitoring.checks.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.energo_monitoring.application.di.PerActivity
import com.example.energo_monitoring.application.di.vm.ViewModelFactory
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.viewmodel.DeviceInspectionVM
import com.example.energo_monitoring.checks.ui.viewmodel.GeneralInspectionVM
import com.example.energo_monitoring.checks.ui.viewmodel.devices.PressureTransducerVM
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Имена-классификаторы для инъекции [ViewModel] в обход прямого вызова конструктора
 */
internal const val VM_GENERAL_INSPECTION_VM = "GeneralInspectionVM"
internal const val VM_DEVICE_INSPECTION_VM = "DeviceInspectionVM"

internal const val VM_PRESSURE_TRANSDUCER_VM = "PressureTransducerVM"

@Module
class FactoryVmModule {

    @Provides
    @Named(VM_GENERAL_INSPECTION_VM)
    fun provideGeneralInspectionVM(
        activity: CheckMainActivity,
        viewModelFactory: ViewModelFactory
    ): GeneralInspectionVM =
        ViewModelProvider(activity, viewModelFactory)[GeneralInspectionVM::class.java]

    @Provides
    @Named(VM_DEVICE_INSPECTION_VM)
    fun provideDeviceInspectionVM(
        activity: CheckMainActivity,
        viewModelFactory: ViewModelFactory
    ): DeviceInspectionVM =
        ViewModelProvider(activity, viewModelFactory)[DeviceInspectionVM::class.java]

    @Provides
    @Named(VM_PRESSURE_TRANSDUCER_VM)
    fun providePressureTransducerVM(
        activity: CheckMainActivity,
        viewModelFactory: ViewModelFactory
    ): PressureTransducerVM =
        ViewModelProvider(activity, viewModelFactory)[PressureTransducerVM::class.java]

}