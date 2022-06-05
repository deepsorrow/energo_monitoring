package com.example.energy_monitoring.checks.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.energy_monitoring.application.di.PerFragment
import com.example.energy_monitoring.application.di.vm.ViewModelFactory
import com.example.energy_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energy_monitoring.checks.ui.fragments.devices.*
import com.example.energy_monitoring.checks.ui.fragments.dialogs.AddNewDeviceDialog
import com.example.energy_monitoring.checks.ui.fragments.screens.HostCheckLengthsFragment
import com.example.energy_monitoring.checks.ui.vm.*
import com.example.energy_monitoring.checks.ui.vm.devices.FlowTransducerVM
import com.example.energy_monitoring.checks.ui.vm.devices.PressureTransducerVM
import com.example.energy_monitoring.checks.ui.vm.devices.TemperatureCounterVM
import com.example.energy_monitoring.checks.ui.vm.devices.TemperatureTransducerVM
import com.example.energy_monitoring.checks.ui.vm.hosts.HostCheckLengthVM
import com.example.energy_monitoring.checks.ui.vm.hosts.HostDevicesVM
import com.example.energy_monitoring.checks.ui.vm.hosts.HostMetricsVM
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Имена-классификаторы для инъекции [ViewModel] в обход прямого вызова конструктора
 */
internal const val VM_GENERAL_INSPECTION_VM = "GeneralInspectionVM"
internal const val VM_DEVICE_INSPECTION_VM = "DeviceInspectionVM"
internal const val VM_HOST_CHECK_LENGTH_VM = "HostCheckLengthsVM"
internal const val VM_HOST_METRICS_VM = "HostMetricsVM"
internal const val VM_FINAL_PHOTOS_VM = "FinalPhotosVM"

internal const val VM_TEMPERATURE_COUNTER_VM = "TemperatureCounterVM"
internal const val VM_TEMPERATURE_TRANSDUCER_VM = "TemperatureTransducerVM"
internal const val VM_FLOW_TRANSDUCER_VM = "FlowTransducerVM"
internal const val VM_PRESSURE_TRANSDUCER_VM = "PressureTransducerVM"
internal const val VM_CHECK_LENGTH_VM = "CheckLengthVM"
internal const val VM_TEMP_METRICS_VM = "TempMetricsVM"

internal const val VM_ADD_NEW_DEVICE_VM = "AddNewDeviceVM"

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
    ): HostDevicesVM =
        ViewModelProvider(activity, viewModelFactory)[HostDevicesVM::class.java]

    @Provides
    @Named(VM_TEMPERATURE_COUNTER_VM)
    fun provideTemperatureCounterVM(
        fragment: TemperatureCounterFragment,
        viewModelFactory: ViewModelFactory
    ): TemperatureCounterVM =
        ViewModelProvider(fragment, viewModelFactory)[TemperatureCounterVM::class.java]

    @Provides
    @Named(VM_TEMPERATURE_TRANSDUCER_VM)
    fun provideTemperatureTransducerVM(
        fragment: TemperatureTransducerFragment,
        viewModelFactory: ViewModelFactory
    ): TemperatureTransducerVM =
        ViewModelProvider(fragment, viewModelFactory)[TemperatureTransducerVM::class.java]

    @Provides
    @Named(VM_FLOW_TRANSDUCER_VM)
    fun provideFlowTransducerVM(
        fragment: FlowTransducerFragment,
        viewModelFactory: ViewModelFactory
    ): FlowTransducerVM =
        ViewModelProvider(fragment, viewModelFactory)[FlowTransducerVM::class.java]

    @Provides
    @Named(VM_PRESSURE_TRANSDUCER_VM)
    fun providePressureTransducerVM(
        fragment: PressureTransducerFragment,
        viewModelFactory: ViewModelFactory
    ): PressureTransducerVM =
        ViewModelProvider(fragment, viewModelFactory)[PressureTransducerVM::class.java]

    @Provides
    @Named(VM_HOST_CHECK_LENGTH_VM)
    fun provideHostCheckLength(
        hostCheckLengthsFragment: HostCheckLengthsFragment,
        viewModelFactory: ViewModelFactory
    ): HostCheckLengthVM =
        ViewModelProvider(hostCheckLengthsFragment, viewModelFactory)[HostCheckLengthVM::class.java]

    @Provides
    @Named(VM_HOST_METRICS_VM)
    fun provideHostMetrics(
        activity: CheckMainActivity,
        viewModelFactory: ViewModelFactory
    ): HostMetricsVM =
        ViewModelProvider(activity, viewModelFactory)[HostMetricsVM::class.java]

    @Provides
    @Named(VM_TEMP_METRICS_VM)
    fun provideTempMetrics(
        fragment: TempCounterMetricsFragment,
        viewModelFactory: ViewModelFactory
    ): TempCounterMetricsVM =
        ViewModelProvider(fragment, viewModelFactory)[TempCounterMetricsVM::class.java]

    @Provides
    @Named(VM_FINAL_PHOTOS_VM)
    fun provideFinalPhotos(
        activity: CheckMainActivity,
        viewModelFactory: ViewModelFactory
    ): FinalPhotosVM =
        ViewModelProvider(activity, viewModelFactory)[FinalPhotosVM::class.java]

    @Provides
    @Named(VM_ADD_NEW_DEVICE_VM)
    fun provideAddNewDevice(
        fragment: AddNewDeviceDialog,
        viewModelFactory: ViewModelFactory
    ): AddNewDeviceVM =
        ViewModelProvider(fragment, viewModelFactory)[AddNewDeviceVM::class.java]
}