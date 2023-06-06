package com.example.weather_forecast_new.domain.models

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("name") val city: String,
    @SerializedName("localtime") val time: String,
    @SerializedName("text") val condition: String,
    @SerializedName("icon") val imageURL: String,
    @SerializedName("code") val imageCode: String,
    @SerializedName("temp_c") val currentTemp: String,
    @SerializedName("is_day") val dayOrNight: String,
    @SerializedName("feelslike_c") val currentTempFeelsLike: String,
    @SerializedName("maxtemp_c") val maxTemp: String,
    @SerializedName("mintemp_c") val minTemp: String,
    @SerializedName("hour") val hours: MutableList<WeatherModel>,//String
    @SerializedName("forecastDays") val forecastDays: MutableList<WeatherModel>,
    @SerializedName("sunrise") val sunrise: String,
    @SerializedName("sunset") val sunset: String,
    @SerializedName("pressure_mb") val pressure: String,
    @SerializedName("wind_kph") val wind: String,
    @SerializedName("daily_chance_of_rain") val dailyChanceOfRain: String?,
    @SerializedName("daily_chance_of_snow") val dailyChanceOfSnow: String?
)
