package com.example.energo_monitoring.checks.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.energo_monitoring.application.di.vm.ViewModelFactory
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.vm.DeviceInspectionVM
import com.example.energo_monitoring.checks.ui.vm.GeneralInspectionVM
import com.example.energo_monitoring.checks.ui.vm.devices.FlowTransducerVM
import com.example.energo_monitoring.checks.ui.vm.devices.PressureTransducerVM
import com.example.energo_monitoring.checks.ui.vm.devices.TemperatureCounterVM
import com.example.energo_monitoring.checks.ui.vm.devices.TemperatureTransducerVM
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Имена-классификаторы для инъекции [ViewModel] в обход прямого вызова конструктора
 */
internal const val VM_GENERAL_INSPECTION_VM = "GeneralInspectionVM"
internal const val VM_DEVICE_INSPECTION_VM = "DeviceInspectionVM"

internal const val VM_TEMPERATURE_COUNTER_VM = "TemperatureCounterVM"
internal const val VM_TEMPERATURE_TRANSDUCER_VM = "TemperatureTransducerVM"
internal const val VM_FLOW_TRANSDUCER_VM = "FlowTransducerVM"
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
    @Named(VM_TEMPERATURE_COUNTER_VM)
    fun provideTemperatureCounterVM(
        activity: CheckMainActivity,
        viewModelFactory: ViewModelFactory
    ): TemperatureCounterVM =
        ViewModelProvider(activity, viewModelFactory)[TemperatureCounterVM::class.java]

    @Provides
    @Named(VM_TEMPERATURE_TRANSDUCER_VM)
    fun provideTemperatureTransducerVM(
        activity: CheckMainActivity,
        viewModelFactory: ViewModelFactory
    ): TemperatureTransducerVM =
        ViewModelProvider(activity, viewModelFactory)[TemperatureTransducerVM::class.java]

    @Provides
    @Named(VM_FLOW_TRANSDUCER_VM)
    fun provideFlowTransducerVM(
        activity: CheckMainActivity,
        viewModelFactory: ViewModelFactory
    ): FlowTransducerVM =
        ViewModelProvider(activity, viewModelFactory)[FlowTransducerVM::class.java]

    @Provides
    @Named(VM_PRESSURE_TRANSDUCER_VM)
    fun providePressureTransducerVM(
        activity: CheckMainActivity,
        viewModelFactory: ViewModelFactory
    ): PressureTransducerVM =
        ViewModelProvider(activity, viewModelFactory)[PressureTransducerVM::class.java]

}