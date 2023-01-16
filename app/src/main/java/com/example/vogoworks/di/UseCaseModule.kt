package com.example.vogoworks.di

import com.jdm.domain.repository.LocationRepository
import com.jdm.domain.usecase.GetAllAlarmInfoUseCase
import com.jdm.domain.usecase.InsertAlarmInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetAllAlarmInfoUseCase(locationRepository: LocationRepository): GetAllAlarmInfoUseCase {
        return GetAllAlarmInfoUseCase(locationRepository)
    }

    @Provides
    fun provideInsertAlarmInfoUseCase(locationRepository: LocationRepository): InsertAlarmInfoUseCase {
        return InsertAlarmInfoUseCase(locationRepository)
    }
}