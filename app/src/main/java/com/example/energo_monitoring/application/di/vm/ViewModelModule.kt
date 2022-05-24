package com.example.energo_monitoring.application.di.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.energo_monitoring.checks.ui.viewmodel.DeviceInspectionVM
import com.example.energo_monitoring.checks.ui.viewmodel.GeneralInspectionVM
import com.example.energo_monitoring.checks.ui.viewmodel.devices.PressureTransducerVM
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
    @ViewModelKey(PressureTransducerVM::class)
    abstract fun providePressureTransducerVM(viewModel: PressureTransducerVM): ViewModel
}