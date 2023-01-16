package com.jdm.domain.model

data class AlarmInfo(
    val id: Long = 0,
    var startHour: Int = -1,
    var startMinute: Int = -1,
    var endHour: Int = -1,
    var endMinute: Int = -1,
    var latitude: Float = 0f,
    var longitude: Float = 0f,
    var isOut: Boolean = true
)
