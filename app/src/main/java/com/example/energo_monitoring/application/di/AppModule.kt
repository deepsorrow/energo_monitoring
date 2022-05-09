package com.example.energo_monitoring.application.di

import com.example.energo_monitoring.EnergomonitoringApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: EnergomonitoringApplication) {
    @Singleton
    @Provides
    fun provideApplication() = application
}