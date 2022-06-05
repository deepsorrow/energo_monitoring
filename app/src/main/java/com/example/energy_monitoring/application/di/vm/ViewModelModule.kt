package com.example.energy_monitoring.application.di.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.energy_monitoring.checks.di.modules.VM_CHECK_LENGTH_VM
import com.example.energy_monitoring.checks.ui.vm.*
import com.example.energy_monitoring.checks.ui.vm.hosts.HostDevicesVM
import com.example.energy_monitoring.checks.ui.vm.hosts.HostCheckLengthVM
import com.example.energy_monitoring.checks.ui.vm.devices.FlowTransducerVM
import com.example.energy_monitoring.checks.ui.vm.devices.PressureTransducerVM
import com.example.energy_monitoring.checks.ui.vm.devices.TemperatureCounterVM
import com.example.energy_monitoring.checks.ui.vm.devices.TemperatureTransducerVM
import com.example.energy_monitoring.checks.ui.vm.hosts.HostMetricsVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GeneralInspectionVM::class)
    abstract fun provideGeneralInspectionViewModel(viewModel: GeneralInspectionVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HostDevicesVM::class)
    abstract fun provideDeviceInspectionVM(viewModel: HostDevicesVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TemperatureCounterVM::class)
    abstract fun provideTemperatureCounterVM(viewModel: TemperatureCounterVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TemperatureTransducerVM::class)
    abstract fun provideTemperatureTransducerVM(viewModel: TemperatureTransducerVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlowTransducerVM::class)
    abstract fun provideFlowTransducerVM(viewModel: FlowTransducerVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PressureTransducerVM::class)
    abstract fun providePressureTransducerVM(viewModel: PressureTransducerVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HostCheckLengthVM::class)
    abstract fun provideHostCheckLengthVM(viewModel: HostCheckLengthVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckLengthVM::class)
    abstract fun provideCheckLengthVM(viewModel: CheckLengthVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HostMetricsVM::class)
    abstract fun provideHostMetricsVM(viewModel: HostMetricsVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TempCounterMetricsVM::class)
    abstract fun provideMetricsVM(viewModel: TempCounterMetricsVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FinalPhotosVM::class)
    abstract fun provideFinalPhotosVM(viewModel: FinalPhotosVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddNewDeviceVM::class)
    abstract fun addNewDeviceVM(viewModel: AddNewDeviceVM): ViewModel
}