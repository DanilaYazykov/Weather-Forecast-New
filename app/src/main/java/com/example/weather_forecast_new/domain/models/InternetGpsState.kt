package com.example.weather_forecast_new.domain.models

data class InternetGpsState(
    val internetData: Boolean,
    val gpsData: Boolean) {
    companion object {
        fun default() = InternetGpsState(
            internetData = true,
            gpsData = true
        )
    }
}
