package com.example.energo_monitoring.checks.di

import com.example.energo_monitoring.checks.activities.CheckMainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [CheckSubcomponent::class])
abstract class CheckModule() {
    //abstract fun inject(fragment: Step3_GeneralInspectionFragment)
    @Binds
    @IntoMap
    @ClassKey(CheckMainActivity::class)
    abstract fun bindAndroidInjectorFactory(factory: CheckSubcomponent.Factory): AndroidInjector.Factory<*>
}