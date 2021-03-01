package com.example.weatherforecastdemo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherforecastdemo.data.model.location.LocationInfo
import com.example.weatherforecastdemo.data.model.weather.WeatherResponse

/**
 * Created by  on 28/02/21.
 */

@Database(entities = [LocationInfo::class,WeatherResponse::class], version = 1)
@TypeConverters(WeatherTypeConvertor::class,ListTypeConvertor::class)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationInfoDao

    abstract fun weatherDao(): WeatherDao
}