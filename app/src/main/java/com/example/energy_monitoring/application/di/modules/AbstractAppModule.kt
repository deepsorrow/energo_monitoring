package com.example.energy_monitoring.application.di.modules

import android.app.Application
import com.example.energy_monitoring.EnergomonitoringApplication
import dagger.Binds
import dagger.Module

@Module
abstract class AbstractAppModule {
    @Binds
    abstract fun bindApplication(application: EnergomonitoringApplication): Application
}