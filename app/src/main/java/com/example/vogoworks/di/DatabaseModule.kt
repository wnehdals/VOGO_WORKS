package com.example.vogoworks.di

import android.content.Context
import androidx.room.Room
import com.jdm.data.source.local.AppDatabase
import com.jdm.data.source.local.dao.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "VogoWorks.db")
            .allowMainThreadQueries()
            .build()
    }
    @Provides
    @Singleton
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao {
        return appDatabase.locationDao()
    }
}