package com.example.weatherforecastdemo.util

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherforecastdemo.WeatherApp

/**
 * Created by  on 28/02/21.
 */
object SharedPreferenceManager {

    private val sharedPreferenceManager: SharedPreferences
    private const val PREF_SHARED_PREFERENCES = "WeatherSharedPreferences"
    private const val LAST_LOCATION = "last_location"

    init {
        sharedPreferenceManager = WeatherApp.getInstance().getSharedPreferences(
            PREF_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    }

    fun saveLastLocation( time:Long){
        sharedPreferenceManager.edit().putLong(LAST_LOCATION, time).apply()
    }

    fun getLastSavedLocation( ): Long {
        return sharedPreferenceManager.getLong(LAST_LOCATION,0)
    }
}