package com.example.energo_monitoring.compose.di

import com.example.energo_monitoring.checks.data.api.ServerApi
import com.example.energo_monitoring.checks.data.api.ServerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HiltModule{

    @Provides
    fun provideServerApi(): ServerApi = ServerService.getService()
}