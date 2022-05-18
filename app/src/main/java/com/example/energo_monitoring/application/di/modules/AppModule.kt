package com.example.energo_monitoring.application.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideContext(application: Application) = application.applicationContext
}