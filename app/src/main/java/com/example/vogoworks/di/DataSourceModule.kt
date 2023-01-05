package com.example.vogoworks.di

import com.jdm.data.source.local.LocalLocationDataSource
import com.jdm.data.source.local.dao.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideLocalLocationDataSource(locationDao: LocationDao): LocalLocationDataSource {
        return LocalLocationDataSource(locationDao)
    }
}