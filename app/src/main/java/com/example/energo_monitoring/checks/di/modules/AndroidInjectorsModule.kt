package com.example.energo_monitoring.checks.di.modules

import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.fragments.devices.*
import com.example.energo_monitoring.checks.ui.fragments.screens.Step3_GeneralInspectionFragment
import com.example.energo_monitoring.checks.ui.fragments.screens.Step4_DeviceInspectionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidInjectorsModule {
    @ContributesAndroidInjector
    abstract fun activity(): CheckMainActivity

    @ContributesAndroidInjector
    abstract fun generalInspection(): Step3_GeneralInspectionFragment

    @ContributesAndroidInjector
    abstract fun deviceInspection(): Step4_DeviceInspectionFragment

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
}