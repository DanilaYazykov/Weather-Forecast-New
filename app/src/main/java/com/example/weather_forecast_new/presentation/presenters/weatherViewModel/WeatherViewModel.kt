package com.example.weather_forecast_new.presentation.presenters.weatherViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_forecast_new.data.network.NetworkResult
import com.example.weather_forecast_new.domain.api.WeatherInteractor
import com.example.weather_forecast_new.domain.models.InternetGpsState
import com.example.weather_forecast_new.domain.models.WeatherModel
import com.example.weather_forecast_new.domain.models.WeatherResult
import com.example.weather_forecast_new.domain.models.WeatherState
import com.example.weather_forecast_new.presentation.presenters.storagePresenter.CityStoragePresenter
import com.example.weather_forecast_new.presentation.util.CheckingInternetUtil
import com.example.weather_forecast_new.presentation.util.LocationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val locationManager: LocationManager,
    private val weatherInteractor: WeatherInteractor,
    private val internet : CheckingInternetUtil,
    cityStorage: CityStoragePresenter
) : ViewModel() {

    private var networkCheckJobInternet: Job? = null
    private var networkCheckJobGps: Job? = null
    private val sharedPreferences = cityStorage

    private val _liveDataWeather = MutableLiveData(WeatherState.default())
    val liveDataWeather = _liveDataWeather

    private val _liveDataInternetGps = MutableLiveData(InternetGpsState.default())
    val liveDataInternetGps = _liveDataInternetGps


    fun getPreviousWeather() {
        val city = sharedPreferences.getSavedDataCity()
        if (city.isNotEmpty()) {
            _liveDataWeather.postValue(_liveDataWeather.value?.copy(currentWeather = WeatherResult.Success(
                city[0]
            )
            ))
        }
    }

    fun getLocation() {
        checkNetwork()
        enableGPS()
        CoroutineScope(Dispatchers.IO).launch {
        val location = locationManager.getLocation()
        loadWeather(location)
        }
    }

    private fun enableGPS() {
        networkCheckJobGps?.cancel()
        networkCheckJobGps = CoroutineScope(Dispatchers.Main).launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            val gps = locationManager.isLocationEnabled()
            _liveDataInternetGps.postValue(_liveDataInternetGps.value?.copy(gpsData = gps))
        }
    }

    private fun checkNetwork() {
        networkCheckJobInternet?.cancel()
        networkCheckJobInternet = CoroutineScope(Dispatchers.IO).launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            val result = internet.isNetworkAvailable()
            _liveDataInternetGps.postValue(_liveDataInternetGps.value?.copy(internetData = result))
        }
    }

    fun loadWeather(name: String) {
        viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            sharedPreferences.saveCity(name)
            weatherInteractor.getWeatherFromAPI(city = name, weatherInfoConsumer = weatherConsumer)
        }
    }

    private val weatherConsumer: WeatherInteractor.WeatherInfoConsumer =
        object : WeatherInteractor.WeatherInfoConsumer {
            override fun onWeatherInfoReceived(weatherInfo: Pair<NetworkResult, WeatherModel>) {
                when (weatherInfo.first) {
                    NetworkResult.SUCCESS -> {
                        sharedPreferences.saveDataCity(arrayListOf(weatherInfo.second))
                        _liveDataWeather.value = _liveDataWeather.value?.copy(currentWeather = WeatherResult.Success(
                            weatherInfo.second
                        )
                        )
                        getHoursList(weatherInfo.second)
                    }

                    NetworkResult.ERROR -> {
                        _liveDataWeather.postValue(_liveDataWeather.value?.copy(currentWeather = WeatherResult.Error))
                    }

                    NetworkResult.NOT_FOUND -> {
                        _liveDataWeather.postValue(_liveDataWeather.value?.copy(currentWeather = WeatherResult.NoData))
                    }
                }
            }
        }

    private fun getHoursList(wItem: WeatherModel) {
        _liveDataWeather.value = _liveDataWeather.value?.copy(forecastHoursWeather = wItem.hours)
        _liveDataWeather.value = _liveDataWeather.value?.copy(forecastDaysWeather = wItem.forecastDays)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1000L
    }
}