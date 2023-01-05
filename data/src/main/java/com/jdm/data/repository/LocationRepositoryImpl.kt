package com.jdm.data.repository

import com.jdm.data.source.local.LocalLocationDataSource
import com.jdm.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val localLocationDataSource: LocalLocationDataSource
): LocationRepository {

}