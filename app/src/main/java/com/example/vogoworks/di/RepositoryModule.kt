package com.example.vogoworks.di

import com.jdm.data.repository.LocationRepositoryImpl
import com.jdm.data.source.local.LocalLocationDataSource
import com.jdm.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideMovieRepository(localLocationDataSource: LocalLocationDataSource): LocationRepository {
        return LocationRepositoryImpl(localLocationDataSource)
    }
}