package com.example.weatherforecastdemo.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecastdemo.R
import com.example.weatherforecastdemo.WeatherApp
import com.example.weatherforecastdemo.data.model.location.LocationInfo
import com.example.weatherforecastdemo.ui.fragment.WeatherFragment
import com.example.weatherforecastdemo.util.DialogUtils
import com.example.weatherforecastdemo.util.LocationHelper
import com.example.weatherforecastdemo.util.LocationListener
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsStatusCodes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class WeatherHomeActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadWeatherFragment()
        getUserPermission()
    }

    private fun loadWeatherFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, WeatherFragment.newInstance())
            .commit()
    }

    private fun getUserPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (LocationHelper.checkLocationSelfPermission(this)) {
                fetchUserLocation(this)
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    DialogUtils.showAlertDialog(
                        this, getString(R.string.location_permission_title),
                        getString(R.string.location_permission_description)
                    ) {
                        requestPermissions(
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_PERMISSIONS_CODE_LOCATION
                        )
                    }
                } else {
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSIONS_CODE_LOCATION
                    )
                }
            }   
        }
    }

    private fun fetchUserLocation(it: Context) {
        LocationHelper.getLocation(it, object : LocationListener {
            override fun onLocationResult(location: Location) {
                saveUserLocation(location)
            }

            override fun onFailure(error: Exception) {
                if(error is ApiException){
                    when ((error as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            showLocationSetting(error)
                        }
                    }
                }
            }
        })
    }

    private fun saveUserLocation(location: Location) {
        val db = WeatherApp.getRoomDatabase()
        CoroutineScope(Dispatchers.IO).launch {
            db.locationDao().insert(
                LocationInfo(
                    Random().nextInt(),
                    location.latitude,
                    location.longitude
                )
            )
        }
    }

    private fun showLocationSetting(error: Exception) {
        try {
            val resolvable = error as ResolvableApiException
            resolvable.startResolutionForResult(
                this@WeatherHomeActivity, REQUEST_CODE_CHECK_SETTINGS
            )
        } catch (sendEx: IntentSender.SendIntentException) {
           Log.e("location setting error",""+sendEx.localizedMessage)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS_CODE_LOCATION) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                fetchUserLocation(this)
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_CHECK_SETTINGS == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                fetchUserLocation(this)
            } else {
                Toast.makeText(this, getString(R.string.location_required_message), Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val REQUEST_PERMISSIONS_CODE_LOCATION = 1
        const val REQUEST_CODE_CHECK_SETTINGS = 2
    }
}