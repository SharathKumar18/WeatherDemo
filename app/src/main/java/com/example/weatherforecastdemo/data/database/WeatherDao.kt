package com.example.weatherforecastdemo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherforecastdemo.data.model.weather.WeatherResponse

/**
 * Created by  on 01/03/21.
 */
@Dao
interface WeatherDao {

    @Insert
    fun insert(response: WeatherResponse)

    @Delete
    fun delete(response: WeatherResponse)

    @Query("SELECT * FROM WeatherResponse ORDER BY id DESC LIMIT 1")
    suspend fun getLastWeather(): WeatherResponse
}