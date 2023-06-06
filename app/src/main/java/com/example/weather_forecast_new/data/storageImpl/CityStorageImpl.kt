package com.example.weather_forecast_new.data.storageImpl

import android.content.SharedPreferences
import com.example.weather_forecast_new.domain.models.WeatherModel
import com.example.weather_forecast_new.domain.storageInteractor.CityStorage
import com.google.gson.Gson

class CityStorageImpl(
    private val sharedPrefs: SharedPreferences,
    private val sharedPrefsData: SharedPreferences
) : CityStorage {

    override fun getSavedCity(): String {
        val savedCity = sharedPrefs.getString(CITIES, null)
        val city = Gson().fromJson(savedCity, Array<String>::class.java)
        return if (city != null) {
            city[0]
        } else {
            EMPTY_RESULT
        }
    }

    override fun saveCity(city: String) {
        val cityJson = Gson().toJson(arrayOf(city))
        sharedPrefs.edit()
            .putString(CITIES, cityJson)
            .apply()
    }

    override fun getSavedDataCity(): ArrayList<WeatherModel> {
        val savedData = sharedPrefsData.getString(DATA, null)
        val data = Gson().fromJson(savedData, Array<WeatherModel>::class.java)
        return if (data != null) {
            ArrayList(data.toList())
        } else {
            ArrayList()
        }
    }

    override fun saveDataCity(data: ArrayList<WeatherModel>) {
        val dataJson = Gson().toJson(data.toArray())
        sharedPrefsData.edit()
            .putString(DATA, dataJson)
            .apply()
    }

    companion object {
        private const val DATA = "dataMainWeather!"
        private const val CITIES = "citiesMainWeather!"
        private const val EMPTY_RESULT = ""
    }
}