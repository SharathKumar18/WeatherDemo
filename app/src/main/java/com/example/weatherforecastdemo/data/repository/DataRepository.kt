package com.example.weatherforecastdemo.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecastdemo.BuildConfig
import com.example.weatherforecastdemo.data.database.LocationDatabase
import com.example.weatherforecastdemo.data.model.location.LocationInfo
import com.example.weatherforecastdemo.data.model.weather.WeatherResponse
import com.example.weatherforecastdemo.data.repository.api.WeatherDataService
import io.reactivex.Observable

/**
 * Created by  on 28/02/21.
 */
class DataRepository(private val dataService: WeatherDataService, private val localDatabase: LocationDatabase) :DataRepositoryInterface{

    override fun getLastLocation(): LiveData<LocationInfo> {
        return localDatabase.locationDao().getLastLocation()
    }

    override fun getCurrentWeatherInfo(lat:Double,lng:Double): Observable<WeatherResponse> {
        return dataService.getCurrentWeather(lat,lng,BuildConfig.weather_api_key)
    }

    override fun saveWeatherDataToDB(weatherResponse: WeatherResponse) {
        localDatabase.weatherDao().insert(weatherResponse)
    }

    override suspend fun getWeatherDataFromDB(): WeatherResponse {
        return localDatabase.weatherDao().getLastWeather()
    }
}