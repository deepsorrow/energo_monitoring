package com.example.energo_monitoring.application.di.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.energo_monitoring.checks.ui.vm.DeviceInspectionVM
import com.example.energo_monitoring.checks.ui.vm.GeneralInspectionVM
import com.example.energo_monitoring.checks.ui.vm.devices.FlowTransducerVM
import com.example.energo_monitoring.checks.ui.vm.devices.PressureTransducerVM
import com.example.energo_monitoring.checks.ui.vm.devices.TemperatureCounterVM
import com.example.energo_monitoring.checks.ui.vm.devices.TemperatureTransducerVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

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
    @ViewModelKey(DeviceInspectionVM::class)
    abstract fun provideDeviceInspectionVM(viewModel: DeviceInspectionVM): ViewModel

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
}