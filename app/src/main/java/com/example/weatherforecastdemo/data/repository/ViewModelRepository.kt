package com.example.weatherforecastdemo.data.repository

import com.example.weatherforecastdemo.dataparser.WeatherDataParser
import com.example.weatherforecastdemo.viewmodel.WeatherViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by  on 28/02/21.
 */
object ViewModelRepository {

    fun provideWeatherViewModel(): WeatherViewModel {

        val repository = DataRepositoryProvider.provideDataRepository()
        val ioScheduler = Schedulers.io()
        val mainScheduler = AndroidSchedulers.mainThread()
        val dealsListParser = WeatherDataParser(repository, ioScheduler, mainScheduler)
        return WeatherViewModel(dealsListParser)
    }
}