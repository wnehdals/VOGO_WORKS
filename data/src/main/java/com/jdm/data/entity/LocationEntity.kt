package com.jdm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val startHour: Int = -1,
    val startMinute: Int = -1,
    val endHour: Int = -1,
    val endMinute: Int = -1,
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val isOut: Boolean = true
)
