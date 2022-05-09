package com.example.energo_monitoring.checks.di.modules

import android.content.Context
import com.example.energo_monitoring.application.di.PerActivity
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import com.example.energo_monitoring.checks.di.DATA_ID
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CheckModule {

    @Named(DATA_ID)
    @Provides
    fun provideDataId(activity: CheckMainActivity) = activity.dataId

    @Provides
    fun provideDatabase(context: Context) = ResultDataDatabase.getDatabase(context)

    @Provides
    fun provideDatabaseDao(context: Context) = ResultDataDatabase.getDatabase(context).resultDataDAO()
}