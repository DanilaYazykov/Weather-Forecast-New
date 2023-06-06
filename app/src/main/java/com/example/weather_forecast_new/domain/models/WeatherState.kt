package com.example.weather_forecast_new.domain.models

data class WeatherState(
    val currentWeather: WeatherResult?,
    val forecastHoursWeather: List<WeatherModel>,
    val forecastDaysWeather: List<WeatherModel>) {
        companion object {
            fun default() = WeatherState(
                currentWeather = WeatherResult.SavedData,
                forecastHoursWeather = emptyList(),
                forecastDaysWeather = emptyList()
            )
        }
    }