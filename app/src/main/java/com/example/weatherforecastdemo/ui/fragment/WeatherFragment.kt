package com.example.weatherforecastdemo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastdemo.R
import com.example.weatherforecastdemo.WeatherApp
import com.example.weatherforecastdemo.data.model.UIData
import com.example.weatherforecastdemo.data.model.weather.WeatherResponse
import com.example.weatherforecastdemo.data.repository.ViewModelRepository
import com.example.weatherforecastdemo.util.Constants
import com.example.weatherforecastdemo.util.SharedPreferenceManager
import com.example.weatherforecastdemo.util.ViewModelFactory
import com.example.weatherforecastdemo.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(ViewModelRepository.provideWeatherViewModel())
        ).get(WeatherViewModel::class.java)
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataObservers()
    }

    private fun setDataObservers() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            setUpViews(it)
        })

        viewModel.getUserLocation().observe(viewLifecycleOwner, Observer {
            if (it != null) viewModel.getLastLocation(it.lat ?: 0.0, it.lng ?: 0.0)
        })

        viewModel.defaultUiLiveData.observe(viewLifecycleOwner, Observer {
            handleProgressCallbacks(it)
        })
    }

    private fun setUpViews(it: WeatherResponse?) {
        it?.let {
          city_name.text=it.name
          temp.text=it.main?.temp.toString()
          temp_description.text= it.weather?.get(0)?.description.toString()
          min_temp.text=it.main?.tempMin.toString()
          max_temp.text=it.main?.tempMax.toString()
        }
    }

    private fun handleProgressCallbacks(uiHelper: UIData) {
        when (uiHelper.status) {
            Constants.SHOW_PROGRESS -> progress_bar.visibility = View.VISIBLE

            Constants.HIDE_PROGRESS -> progress_bar.visibility = View.GONE
        }
    }
}