package com.example.weatherforecastdemo.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.weatherforecastdemo.WeatherApp
import com.example.weatherforecastdemo.data.model.location.LocationInfo
import com.example.weatherforecastdemo.ui.fragment.WeatherFragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Created by  on 28/02/21.
 */
object LocationHelper {

    private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 7200000

    private var fusedLocation: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(WeatherApp.getInstance())
    private var settingsClient: SettingsClient =
        LocationServices.getSettingsClient(WeatherApp.getInstance())
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private lateinit var locationCallback: LocationCallback
    private var listener: LocationListener? = null

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                listener?.onLocationResult(locationResult.lastLocation)
                SharedPreferenceManager.saveLastLocation(System.currentTimeMillis() + UPDATE_INTERVAL_IN_MILLISECONDS)
            }
        }
    }

    private fun createLocationSettingRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        locationSettingsRequest = builder.build()
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = UPDATE_INTERVAL_IN_MILLISECONDS / 2
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun getLocation(context: Context, callback: LocationListener) {
        val lastLocation=SharedPreferenceManager.getLastSavedLocation()
        if(System.currentTimeMillis() > lastLocation){
            listener = callback
            createLocationRequest()
            createLocationSettingRequest()
            createLocationCallback()
            settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(context as Activity) {
                    if (checkLocationSelfPermission(context)) {
                        fusedLocation.requestLocationUpdates(
                            locationRequest,
                            locationCallback, Looper.getMainLooper()
                        )
                    }
                }
                .addOnFailureListener(context) { e: Exception ->
                    stopLocationUpdates(context)
                    callback.onFailure(e)
                }
        }else{
            callback.onFailure(java.lang.Exception())
        }
    }

    fun checkLocationSelfPermission(context: Context) = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun stopLocationUpdates(context: Context) {
        fusedLocation.removeLocationUpdates(locationCallback)
            .addOnCompleteListener(context as Activity) { }
    }
}