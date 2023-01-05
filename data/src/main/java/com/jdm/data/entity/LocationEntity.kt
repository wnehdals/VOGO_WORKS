package com.jdm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val latitude: Float,
    val longitude: Float
)
