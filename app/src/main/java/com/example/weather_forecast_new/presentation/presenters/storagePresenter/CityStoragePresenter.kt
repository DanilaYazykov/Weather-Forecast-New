package com.example.weather_forecast_new.presentation.presenters.storagePresenter

import com.example.weather_forecast_new.domain.models.WeatherModel
import com.example.weather_forecast_new.domain.storageInteractor.CityStorageInteractor

class CityStoragePresenter(
    private val shared: CityStorageInteractor
) {

    fun getSavedCity(): String {
        return shared.getSavedCity()
    }

    fun saveCity(city: String) {
        shared.saveCity(city)
    }

    fun getSavedDataCity(): ArrayList<WeatherModel> {
        return shared.getSavedDataCity()
    }

    fun saveDataCity(data: ArrayList<WeatherModel>) {
        shared.saveDataCity(data)
    }
}