package com.example.energy_monitoring.checks.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.energy_monitoring.application.di.PerFragment
import com.example.energy_monitoring.application.di.vm.ViewModelFactory
import com.example.energy_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energy_monitoring.checks.ui.fragments.CheckLengthFragment
import com.example.energy_monitoring.checks.ui.vm.CheckLengthVM
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FactoryVmModulePerFragment {
    //@PerFragment
    @Provides
    @Named(VM_CHECK_LENGTH_VM)
    fun provideCheckLength(
        fragment: CheckLengthFragment,
        viewModelFactory: ViewModelFactory
    ): CheckLengthVM =
        ViewModelProvider(fragment, viewModelFactory)[CheckLengthVM::class.java]
}