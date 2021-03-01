package com.example.weatherforecastdemo.data.repository

import com.example.weatherforecastdemo.WeatherApp
import com.example.weatherforecastdemo.data.repository.api.ApiService
import com.example.weatherforecastdemo.data.repository.api.WeatherDataService

/**
 * Created by  on 28/02/21.
 */
object DataRepositoryProvider {

    fun provideDataRepository(): DataRepository {
        val retrofit = ApiService.getWeatherApiService()
        val dataService = retrofit.create(WeatherDataService::class.java)
        val locationDatabase = WeatherApp.getRoomDatabase()
        return DataRepository(dataService, locationDatabase)
    }
}