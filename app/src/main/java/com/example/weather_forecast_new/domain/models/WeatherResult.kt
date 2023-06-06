package com.example.weather_forecast_new.domain.models

sealed class WeatherResult {
    data class Success(val weatherModel: WeatherModel) : WeatherResult()
    object Error : WeatherResult()
    object NoData : WeatherResult()
    object SavedData : WeatherResult()
}