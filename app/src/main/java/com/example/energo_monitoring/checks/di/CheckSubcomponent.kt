package com.example.energo_monitoring.checks.di

import com.example.energo_monitoring.checks.activities.CheckMainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface CheckSubcomponent : AndroidInjector<CheckMainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<CheckMainActivity>
}