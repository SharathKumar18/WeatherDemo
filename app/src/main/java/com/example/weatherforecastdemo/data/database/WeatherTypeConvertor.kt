package com.example.weatherforecastdemo.data.database

import androidx.room.TypeConverter
import com.example.weatherforecastdemo.data.model.weather.Main
import com.google.gson.Gson

/**
 * Created by  on 01/03/21.
 */
class WeatherTypeConvertor {

    @TypeConverter
    fun storedStringToMainData(value: String?): Main? {
        val gson = Gson()
        return if (value == null) {
            null
        } else {
            gson.fromJson(value, Main::class.java)
        }
    }

    @TypeConverter
    fun mainDataToStoredString(data: Main?): String? {
        val gson = Gson()
        return if (data == null) {
            ""
        } else {
            gson.toJson(data)
        }
    }
}