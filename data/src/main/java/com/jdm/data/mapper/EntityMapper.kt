package com.jdm.domain.mapper

import com.jdm.data.entity.LocationEntity
import com.jdm.domain.model.AlarmInfo

fun AlarmInfo.toLocationEntity() = LocationEntity(
    id = id,
    latitude = latitude,
    longitude = longitude,
    startHour = startHour,
    startMinute = startMinute,
    endHour = endHour,
    endMinute = endMinute,
    isOut = isOut
)
fun LocationEntity.toAlarmInfo() = AlarmInfo(
    id = id,
    latitude = latitude,
    longitude = longitude,
    startHour = startHour,
    startMinute = startMinute,
    endHour = endHour,
    endMinute = endMinute,
    isOut = isOut
)
