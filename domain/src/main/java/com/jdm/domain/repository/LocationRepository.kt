package com.jdm.domain.repository

import com.jdm.domain.model.AlarmInfo
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun insertAlarmInfo(alarmInfo: AlarmInfo): Long
    fun getAllAlarmInfo(): Flow<List<AlarmInfo>>
}