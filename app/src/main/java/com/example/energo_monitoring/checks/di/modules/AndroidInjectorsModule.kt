package com.example.energo_monitoring.checks.di.modules

import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.fragments.screens.Step3_GeneralInspectionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidInjectorsModule {
    @ContributesAndroidInjector
    abstract fun activity(): CheckMainActivity

    @ContributesAndroidInjector
    abstract fun generalInspection(): Step3_GeneralInspectionFragment
}