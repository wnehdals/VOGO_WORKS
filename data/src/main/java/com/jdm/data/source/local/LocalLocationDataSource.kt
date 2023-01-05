package com.jdm.data.source.local

import com.jdm.data.source.local.dao.LocationDao
import javax.inject.Inject

class LocalLocationDataSource @Inject constructor(
    private val locationDao: LocationDao
) {

}