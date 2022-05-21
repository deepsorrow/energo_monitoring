package com.example.energo_monitoring.compose.di

import android.content.Context
import com.example.energo_monitoring.checks.data.api.ServerApi
import com.example.energo_monitoring.checks.data.api.ServerService
import com.example.energo_monitoring.compose.data.api.RefDocDao
import com.example.energo_monitoring.compose.db.RefDocDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HiltModule{

    @Provides
    fun provideServerApi(): ServerApi = ServerService.service

    @Provides
    fun provideRefDao(@ApplicationContext context: Context): RefDocDao
        = RefDocDatabase.getDatabase(context).dao()
}