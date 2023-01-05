package com.jdm.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jdm.data.entity.LocationEntity
import com.jdm.data.source.local.dao.LocationDao

@Database(
    entities = [LocationEntity::class], version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun locationDao(): LocationDao
}