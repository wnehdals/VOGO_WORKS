package com.jdm.domain.usecase

import com.jdm.domain.model.AlarmInfo
import com.jdm.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllAlarmInfoUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<List<AlarmInfo>> {
        return locationRepository.getAllAlarmInfo()
    }
}