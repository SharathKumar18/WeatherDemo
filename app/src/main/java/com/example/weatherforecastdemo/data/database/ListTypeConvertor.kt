package com.example.weatherforecastdemo.data.database

import androidx.room.TypeConverter
import com.example.weatherforecastdemo.data.model.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.util.*

/**
 * Created by  on 01/03/21.
 */
class ListTypeConvertor {
    @TypeConverter
    fun fromString(value: String?): List<Weather?>? {
        return try {
            if (value == null) {
                Collections.emptyList()
            } else {
                val gson = Gson()
                val listType = object : TypeToken<List<Weather?>?>() {}.type
                gson.fromJson<List<Weather?>>(value, listType)
            }
        } catch (e: Exception){
            Collections.emptyList()
        }
    }

    @TypeConverter
    fun fromArrayList(list: List<Weather?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}