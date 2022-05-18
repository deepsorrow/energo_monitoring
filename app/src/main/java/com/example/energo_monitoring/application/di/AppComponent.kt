package com.example.energo_monitoring.application.di

import android.app.Application
import android.content.Context
import com.example.energo_monitoring.EnergomonitoringApplication
import com.example.energo_monitoring.application.di.modules.AbstractAppModule
import com.example.energo_monitoring.application.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component (modules = [
    AndroidInjectionModule::class,
    AbstractAppModule::class,
    AppModule::class]
)
interface AppComponent : AndroidInjector<EnergomonitoringApplication> {
    override fun inject(application: EnergomonitoringApplication)

    fun application(): Application

    fun context(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: EnergomonitoringApplication): Builder

        fun build(): AppComponent
    }
}