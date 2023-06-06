package com.example.weather_forecast_new.domain.impl_use_cases

import com.example.weather_forecast_new.domain.api.WeatherInteractor
import com.example.weather_forecast_new.domain.api.WeatherRepository
import kotlinx.coroutines.*

class WeatherInteractorImpl(private val weatherRepository: WeatherRepository) : WeatherInteractor {

    override fun getWeatherFromAPI(
        city: String,
        weatherInfoConsumer: WeatherInteractor.WeatherInfoConsumer
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val weather = weatherRepository.getWeatherFromAPI(city)
                withContext(Dispatchers.Main) {
                    weatherInfoConsumer.onWeatherInfoReceived(weather)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}