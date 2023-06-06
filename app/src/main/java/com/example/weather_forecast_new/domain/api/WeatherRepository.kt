package com.example.weather_forecast_new.domain.api

import com.example.weather_forecast_new.data.network.NetworkResult
import com.example.weather_forecast_new.domain.models.WeatherModel

interface WeatherRepository {
    suspend fun getWeatherFromAPI(city: String): Pair<NetworkResult, WeatherModel>
}