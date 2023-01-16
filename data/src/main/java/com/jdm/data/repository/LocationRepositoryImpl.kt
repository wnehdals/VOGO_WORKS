package com.jdm.data.repository

import com.jdm.data.source.local.LocalLocationDataSource
import com.jdm.domain.mapper.toAlarmInfo
import com.jdm.domain.mapper.toLocationEntity
import com.jdm.domain.model.AlarmInfo
import com.jdm.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val localLocationDataSource: LocalLocationDataSource
): LocationRepository {
    override suspend fun insertAlarmInfo(alarmInfo: AlarmInfo): Long {
        return localLocationDataSource.insertLocationEntity(alarmInfo.toLocationEntity())
    }

    override fun getAllAlarmInfo(): Flow<List<AlarmInfo>> {
        return localLocationDataSource.getAllLocationEntity()
            .map {
                it.map { it.toAlarmInfo() }
            }

    }

}