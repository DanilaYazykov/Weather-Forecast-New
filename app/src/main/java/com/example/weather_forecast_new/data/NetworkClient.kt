package com.example.weather_forecast_new.data

interface NetworkClient {
    suspend fun doRequest(dto: Any): Any
}