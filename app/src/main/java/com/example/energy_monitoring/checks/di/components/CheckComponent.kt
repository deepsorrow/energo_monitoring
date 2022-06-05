package com.example.energy_monitoring.checks.di.components

import com.example.energy_monitoring.application.di.AppComponent
import com.example.energy_monitoring.application.di.PerActivity
import com.example.energy_monitoring.application.di.vm.ViewModelModule
import com.example.energy_monitoring.checks.di.DATA_ID
import com.example.energy_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energy_monitoring.checks.di.modules.AndroidInjectorsModule
import com.example.energy_monitoring.checks.di.modules.CheckModule
import com.example.energy_monitoring.checks.di.modules.FactoryVmModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Named

@PerActivity
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

        @BindsInstance
        fun dataId(@Named(DATA_ID) dataId: Int): Builder

        fun build(): CheckComponent
    }
}