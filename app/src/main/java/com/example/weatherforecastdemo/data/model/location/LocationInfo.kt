package com.example.weatherforecastdemo.data.model.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by  on 28/02/21.
 */

@Entity
data class LocationInfo(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "lat") val lat: Double?,
    @ColumnInfo(name = "lng") val lng: Double?
)