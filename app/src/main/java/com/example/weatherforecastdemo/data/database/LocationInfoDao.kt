package com.example.weatherforecastdemo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherforecastdemo.data.model.location.LocationInfo

/**
 * Created by  on 28/02/21.
 */

@Dao
interface LocationInfoDao {

    @Query("SELECT * FROM LocationInfo ORDER BY id DESC LIMIT 1")
    fun getLastLocation(): LiveData<LocationInfo>

    @Query("SELECT * FROM LocationInfo ORDER BY id DESC LIMIT 1")
    fun getLastUserLocation(): LocationInfo

    @Insert
    fun insert(Location: LocationInfo)

}