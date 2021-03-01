package com.example.weatherforecastdemo.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecastdemo.data.model.location.LocationInfo
import com.example.weatherforecastdemo.data.model.weather.WeatherResponse
import io.reactivex.Observable

/**
 * Created by  on 28/02/21.
 */
interface DataRepositoryInterface {

    fun getLastLocation(): LiveData<LocationInfo>

    fun getCurrentWeatherInfo(lat:Double,lng:Double): Observable<WeatherResponse>

    fun saveWeatherDataToDB(weatherResponse: WeatherResponse)

    suspend fun getWeatherDataFromDB() :WeatherResponse
}