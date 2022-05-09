package com.example.energo_monitoring.checks.di

import com.example.energo_monitoring.application.di.AppComponent
import com.example.energo_monitoring.application.di.PerActivity
import com.example.energo_monitoring.application.di.vm.ViewModelModule
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.di.modules.AndroidInjectorsModule
import com.example.energo_monitoring.checks.di.modules.CheckModule
import com.example.energo_monitoring.checks.di.modules.FactoryVmModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(dependencies = [AppComponent::class],
    modules = [AndroidInjectionModule::class,
    AndroidInjectorsModule::class,
    ViewModelModule::class,
    FactoryVmModule::class,
    CheckModule::class]
)
abstract class CheckComponent {

    abstract fun inject(activity: CheckMainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: CheckMainActivity): Builder

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): CheckComponent
    }
}