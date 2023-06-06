package com.example.weather_forecast_new.domain.api

import com.example.weather_forecast_new.data.network.NetworkResult
import com.example.weather_forecast_new.domain.models.WeatherModel

interface WeatherInteractor {

    fun getWeatherFromAPI(city: String, weatherInfoConsumer: WeatherInfoConsumer)

    interface WeatherInfoConsumer {
        fun onWeatherInfoReceived(weatherInfo: Pair<NetworkResult, WeatherModel>)
    }
}