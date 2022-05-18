package com.example.energo_monitoring.application.di.modules

import android.app.Application
import com.example.energo_monitoring.EnergomonitoringApplication
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AbstractAppModule {
    @Binds
    abstract fun bindApplication(application: EnergomonitoringApplication): Application
}