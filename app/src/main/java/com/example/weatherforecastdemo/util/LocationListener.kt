package com.example.weatherforecastdemo.util

import android.location.Location

/**
 * Created by  on 28/02/21.
 */
interface LocationListener {
    fun onLocationResult(location: Location)
    fun onFailure(error: Exception)
}