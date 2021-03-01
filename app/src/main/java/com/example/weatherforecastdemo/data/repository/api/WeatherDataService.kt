package com.example.weatherforecastdemo.data.repository.api

import com.example.weatherforecastdemo.data.model.weather.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by  on 28/02/21.
 */
interface WeatherDataService {

    @GET("weather")
    fun getCurrentWeather(@Query("lat") lat:Double, @Query("lon") lon:Double,
                          @Query("appid")appID:String): Observable<WeatherResponse>
}