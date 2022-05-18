package com.example.energo_monitoring

import android.app.Application
import com.example.energo_monitoring.application.di.AppComponent
import com.example.energo_monitoring.application.di.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // для Hilt в связке с Compose
class EnergomonitoringApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }
}