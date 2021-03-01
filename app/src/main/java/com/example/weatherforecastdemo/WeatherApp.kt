package com.example.weatherforecastdemo

import android.app.Application
import androidx.room.Room
import com.example.weatherforecastdemo.data.database.LocationDatabase

/**
 * Created by  on 28/02/21.
 */
class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        singleton = this
    }

    companion object{
        private lateinit var singleton: WeatherApp
        private lateinit var database: LocationDatabase
        const val DATABASE_NAME="location-database"

        fun getInstance(): WeatherApp {
            return singleton
        }

        fun getRoomDatabase(): LocationDatabase {
            return Room.databaseBuilder(
                singleton.applicationContext,
                LocationDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}