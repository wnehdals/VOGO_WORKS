package com.jdm.domain.usecase

import com.jdm.domain.model.AlarmInfo
import com.jdm.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertAlarmInfoUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    suspend operator fun invoke(alarmInfo: AlarmInfo): Long {
        return locationRepository.insertAlarmInfo(alarmInfo)
    }
}