package com.example.energo_monitoring.application.di

import com.example.energo_monitoring.EnergomonitoringApplication
import com.example.energo_monitoring.application.di.vm.ViewModelModule
import com.example.energo_monitoring.checks.di.CheckModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component (modules = [
    AndroidInjectionModule::class,
    ViewModelModule::class,
    CheckModule::class,
    AppModule::class]
)
interface AppComponent : AndroidInjector<EnergomonitoringApplication> {
    override fun inject(application: EnergomonitoringApplication)
}