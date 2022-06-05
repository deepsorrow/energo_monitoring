package com.example.energy_monitoring.checks.di.modules

import android.content.Context
import com.example.energy_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energy_monitoring.checks.data.db.ResultDataDatabase
import com.example.energy_monitoring.checks.di.DATA_ID
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CheckModule {

    @Provides
    fun provideDatabase(context: Context) = ResultDataDatabase.getDatabase(context)

    @Provides
    fun provideDatabaseDao(context: Context) = ResultDataDatabase.getDatabase(context).resultDataDAO()
}