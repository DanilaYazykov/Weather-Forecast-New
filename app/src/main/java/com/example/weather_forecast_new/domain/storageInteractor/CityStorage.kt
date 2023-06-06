package com.example.weather_forecast_new.domain.storageInteractor

import com.example.weather_forecast_new.domain.models.WeatherModel

interface CityStorage {

    fun getSavedCity(): String

    fun saveCity(city: String)

    fun getSavedDataCity(): ArrayList<WeatherModel>

    fun saveDataCity(data: ArrayList<WeatherModel>)
}