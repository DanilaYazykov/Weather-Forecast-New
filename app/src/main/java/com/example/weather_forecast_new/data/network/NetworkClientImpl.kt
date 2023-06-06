package com.example.weather_forecast_new.data.network

import com.example.weather_forecast_new.data.NetworkClient
import com.example.weather_forecast_new.data.dto.RequestGetWeather
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.*

class NetworkClientImpl(private val weatherApi: WeatherApi) : NetworkClient {

    override suspend fun doRequest(dto: Any): Pair<NetworkResult, Any> = withContext(Dispatchers.IO) {

        val requestDto = dto as RequestGetWeather
        val city = requestDto.city

        try {
        val request: Response<JsonObject> = weatherApi.getWeather(API_KEY, city, DAYS, AQI, ALERTS)
        if (request.isSuccessful && request.body() != null) {
            val json = request.body()!!
            val weatherModel = ParseWeatherData().parseWeatherData(json)
            Pair(NetworkResult.SUCCESS, weatherModel)
        } else if (request.code() == 400) {
            Pair(NetworkResult.NOT_FOUND, "")
        } else {
            Pair(NetworkResult.ERROR, "")
        }
    } catch (e: Exception) {
            Pair(NetworkResult.ERROR, "")
        }
    }

    companion object {
        const val API_KEY = "5323a45321bf43c5a6a120847232501"
        const val DAYS = 7
        const val AQI = "no"
        const val ALERTS = "no"
    }
}