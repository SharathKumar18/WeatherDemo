package com.example.weatherforecastdemo.dataparser

import androidx.lifecycle.LiveData
import com.example.weatherforecastdemo.data.model.location.LocationInfo
import com.example.weatherforecastdemo.data.model.weather.WeatherResponse
import com.example.weatherforecastdemo.data.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by  on 28/02/21.
 */
class WeatherDataParser(
    private val repository: DataRepository,
    private val ioScheduler: Scheduler,
    private val mainScheduler: Scheduler
) : WeatherDataParserInterface{

    override fun getLastLocation(): LiveData<LocationInfo> {
        return repository.getLastLocation()
    }

    override fun getCurrentWeatherInfo(lat: Double, lng: Double): Observable<WeatherResponse> {
        return repository.getCurrentWeatherInfo(lat, lng)
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
    }

    override fun saveWeatherDataToDB(weatherResponse: WeatherResponse) {
        repository.saveWeatherDataToDB(weatherResponse)
    }

    override suspend fun getWeatherDataFromDB(): WeatherResponse {
        return repository.getWeatherDataFromDB()
    }
}