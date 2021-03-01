package com.example.weatherforecastdemo.data.model.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherforecastdemo.data.database.ListTypeConvertor
import com.example.weatherforecastdemo.data.database.WeatherTypeConvertor
import com.google.gson.annotations.SerializedName

/**
 * Created by  on 28/02/21.
 */

@Entity
data class WeatherResponse(
    @PrimaryKey
    var id: Int,
    @SerializedName("weather")
    @TypeConverters(ListTypeConvertor::class)
    var weather: List<Weather?>? = null,
    @SerializedName("main")
    @TypeConverters(WeatherTypeConvertor::class)
    var main: Main? = null,
    @SerializedName("timezone")
    var timezone: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("cod")
    var cod: Int? = null
)