package com.example.energy_monitoring.checks.di.modules

import com.example.energy_monitoring.application.di.PerFragment
import com.example.energy_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energy_monitoring.checks.ui.fragments.CheckLengthFragment
import com.example.energy_monitoring.checks.ui.fragments.devices.*
import com.example.energy_monitoring.checks.ui.fragments.dialogs.AddNewDeviceDialog
import com.example.energy_monitoring.checks.ui.fragments.screens.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidInjectorsModule {
    @ContributesAndroidInjector
    abstract fun activity(): CheckMainActivity

    @ContributesAndroidInjector
    abstract fun generalInspection(): GeneralInspectionFragment

    @ContributesAndroidInjector
    abstract fun deviceInspection(): HostDevicesFragment

    @ContributesAndroidInjector(modules = [FactoryVmModule::class])
    abstract fun hostCheckLengths(): HostCheckLengthsFragment

    @ContributesAndroidInjector
    abstract fun tempMetricsInjection(): HostMetricsFragment

    @ContributesAndroidInjector
    abstract fun finalPhotosInjection(): FinalPhotosFragment

    @ContributesAndroidInjector(modules = [FactoryVmModule::class])
    abstract fun tempCounterInspection(): TempCounterMetricsFragment

    @ContributesAndroidInjector(modules = [FactoryVmModule::class])
    abstract fun flowTransducerInjection(): FlowTransducerFragment

    @ContributesAndroidInjector(modules = [FactoryVmModule::class])
    abstract fun pressureTransducerInjection(): PressureTransducerFragment

    @ContributesAndroidInjector(modules = [FactoryVmModule::class])
    abstract fun temperatureCounterInjection(): TemperatureCounterFragment

    @ContributesAndroidInjector(modules = [FactoryVmModule::class])
    abstract fun temperatureTransducerInjection(): TemperatureTransducerFragment

    //@PerFragment
    @ContributesAndroidInjector(modules = [FactoryVmModulePerFragment::class])
    abstract fun checkLengthInjection(): CheckLengthFragment

    @ContributesAndroidInjector(modules = [FactoryVmModule::class])
    abstract fun addNewDeviceInjection(): AddNewDeviceDialog
}