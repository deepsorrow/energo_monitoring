package com.example.energo_monitoring.checks.di.modules

import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.fragments.CheckLengthFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.*
import com.example.energo_monitoring.checks.ui.fragments.screens.GeneralInspectionFragment
import com.example.energo_monitoring.checks.ui.fragments.screens.DeviceInspectionFragment
import com.example.energo_monitoring.checks.ui.fragments.screens.HostCheckLengthsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidInjectorsModule {
    @ContributesAndroidInjector
    abstract fun activity(): CheckMainActivity

    @ContributesAndroidInjector
    abstract fun generalInspection(): GeneralInspectionFragment

    @ContributesAndroidInjector
    abstract fun deviceInspection(): DeviceInspectionFragment

    @ContributesAndroidInjector(modules = [FactoryVmModule::class])
    abstract fun hostCheckLengths(): HostCheckLengthsFragment

    @ContributesAndroidInjector
    abstract fun counterInspection(): CounterFragment

    @ContributesAndroidInjector
    abstract fun flowTransducerInjection(): FlowTransducerFragment

    @ContributesAndroidInjector
    abstract fun pressureTransducerInjection(): PressureTransducerFragment

    @ContributesAndroidInjector
    abstract fun temperatureCounterInjection(): TemperatureCounterFragment

    @ContributesAndroidInjector
    abstract fun temperatureTransducerInjection(): TemperatureTransducerFragment

    @ContributesAndroidInjector
    abstract fun checkLengthInjection(): CheckLengthFragment
}