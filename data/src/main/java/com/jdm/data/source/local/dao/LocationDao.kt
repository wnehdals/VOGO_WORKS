package com.jdm.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdm.data.entity.LocationEntity
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationEntity: LocationEntity): Long


    @Query("SELECT * FROM LOCATION ORDER BY ID ASC")
    fun loadAll(): Flow<List<LocationEntity>>
}