package com.jdm.data.source.local

import com.jdm.data.entity.LocationEntity
import com.jdm.data.source.local.dao.LocationDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalLocationDataSource @Inject constructor(
    private val locationDao: LocationDao
) {
    suspend fun insertLocationEntity(locationEntity: LocationEntity): Long {
        return locationDao.insert(locationEntity)
    }
    fun getAllLocationEntity(): Flow<List<LocationEntity>> {
        return locationDao.loadAll()
    }
}