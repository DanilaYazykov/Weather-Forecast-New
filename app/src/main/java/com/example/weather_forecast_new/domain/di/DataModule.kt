package com.example.weather_forecast_new.domain.di

import android.content.Context
import android.content.SharedPreferences
import com.example.weather_forecast_new.data.NetworkClient
import com.example.weather_forecast_new.data.WeatherRepositoryImpl
import com.example.weather_forecast_new.data.network.NetworkClientImpl
import com.example.weather_forecast_new.data.network.WeatherApi
import com.example.weather_forecast_new.data.storageImpl.CityStorageImpl
import com.example.weather_forecast_new.domain.api.WeatherRepository
import com.example.weather_forecast_new.domain.storageInteractor.CityStorage
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val dataModule = module {

    single<WeatherApi> {
        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    factory { Gson() }

    single<WeatherRepository> {
        WeatherRepositoryImpl(networkClient = get())
    }

    single<NetworkClient> {
        NetworkClientImpl(weatherApi = get())
    }

    factory<CityStorage> {
        CityStorageImpl(sharedPrefs = get(named("cities")), sharedPrefsData = get(named("data")))
    }

    single<SharedPreferences>(named("cities")) {
        androidContext().getSharedPreferences("cities", Context.MODE_PRIVATE)
    }
    single<SharedPreferences>(named("data")) {
        androidContext().getSharedPreferences("data", Context.MODE_PRIVATE)
    }

}