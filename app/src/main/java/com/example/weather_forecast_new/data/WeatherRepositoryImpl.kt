package com.example.weather_forecast_new.data

import com.example.weather_forecast_new.data.dto.RequestGetWeather
import com.example.weather_forecast_new.data.network.NetworkResult
import com.example.weather_forecast_new.domain.api.WeatherRepository
import com.example.weather_forecast_new.domain.models.WeatherModel

class WeatherRepositoryImpl(private val networkClient: NetworkClient): WeatherRepository {

    override suspend fun getWeatherFromAPI(city: String): Pair<NetworkResult, WeatherModel> {
        @Suppress("UNCHECKED_CAST")
        return networkClient.doRequest(RequestGetWeather(city = city)) as Pair<NetworkResult, WeatherModel>
    }
}

