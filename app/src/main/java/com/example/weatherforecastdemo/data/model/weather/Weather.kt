package com.example.weatherforecastdemo.data.model.weather

import com.google.gson.annotations.SerializedName

/**
 * Created by  on 01/03/21.
 */
data class Weather(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("main")
    var main: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("icon")
    var icon: String? = null
)