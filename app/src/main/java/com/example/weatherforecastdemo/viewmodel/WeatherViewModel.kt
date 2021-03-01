package com.example.weatherforecastdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.weatherforecastdemo.data.model.UIData
import com.example.weatherforecastdemo.data.model.location.LocationInfo
import com.example.weatherforecastdemo.data.model.weather.WeatherResponse
import com.example.weatherforecastdemo.dataparser.WeatherDataParser
import com.example.weatherforecastdemo.util.Constants
import com.example.weatherforecastdemo.util.SharedPreferenceManager
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class WeatherViewModel(private val weatherDataParser: WeatherDataParser) : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    private val defaultUiMutableLiveData = MutableLiveData<UIData>()
    val defaultUiLiveData: LiveData<UIData> get() = defaultUiMutableLiveData

    private val weatherMutableLiveData = MutableLiveData<WeatherResponse>()
    val weatherLiveData: LiveData<WeatherResponse> get() = weatherMutableLiveData

    fun getUserLocation(): LiveData<LocationInfo> {
        return weatherDataParser.getLastLocation()
    }

    fun getLastLocation(lat:Double,lng:Double) {
        val lastLocation= SharedPreferenceManager.getLastSavedLocation()
        if(System.currentTimeMillis() > lastLocation){
            getWeatherDataFromServer(lat, lng)
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                val data= weatherDataParser.getWeatherDataFromDB()
                withContext(Dispatchers.Main){
                    if(data==null){
                        getWeatherDataFromServer(lat,lng)
                    }else weatherMutableLiveData.value =data
                }
            }
        }
    }

    private fun getWeatherDataFromServer(lat: Double, lng: Double) {
        weatherDataParser.getCurrentWeatherInfo(lat, lng)
            .subscribe(object : Observer<WeatherResponse> {
                override fun onComplete() {
                    hideDefaultProgress()
                }

                override fun onSubscribe(d: Disposable) {
                    showDefaultProgress()
                    compositeDisposable.add(d)
                }

                override fun onNext(data: WeatherResponse) {
                    saveDataToDB(data)
                    weatherMutableLiveData.value = data
                }

                override fun onError(e: Throwable) {
                    hideDefaultProgress()
                }
            })
    }

    private fun saveDataToDB(data: WeatherResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            weatherDataParser.saveWeatherDataToDB(data)
        }
    }

    protected fun showDefaultProgress() {
        val helper = UIData(Constants.SHOW_PROGRESS, null)
        defaultUiMutableLiveData.value = helper
    }

    protected fun hideDefaultProgress() {
        val helper = UIData(Constants.HIDE_PROGRESS, null)
        defaultUiMutableLiveData.value = helper
    }
}