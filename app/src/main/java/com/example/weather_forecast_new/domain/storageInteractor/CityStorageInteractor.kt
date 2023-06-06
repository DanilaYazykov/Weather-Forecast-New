package com.example.weather_forecast_new.domain.storageInteractor

import com.example.weather_forecast_new.domain.models.WeatherModel

class CityStorageInteractor(private val sharedPrefs: CityStorage) {

    fun getSavedCity(): String {
        return sharedPrefs.getSavedCity()
    }

    fun saveCity(city: String) {
        sharedPrefs.saveCity(city)
    }

    fun getSavedDataCity(): ArrayList<WeatherModel> {
        return sharedPrefs.getSavedDataCity()
    }

    fun saveDataCity(data: ArrayList<WeatherModel>) {
        sharedPrefs.saveDataCity(data)
    }
}