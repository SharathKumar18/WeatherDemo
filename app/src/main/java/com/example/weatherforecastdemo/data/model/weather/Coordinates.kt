package com.example.weatherforecastdemo.data.model.weather

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


/**
 * Created by  on 01/03/21.
 */
data class Coordinates(
    @SerializedName("lon")
    private var lon: Double? = null,
    @SerializedName("lat")
    private var lat: Double? = null
)