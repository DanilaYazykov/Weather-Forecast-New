package com.example.weather_forecast_new.domain.di

import com.example.weather_forecast_new.domain.api.WeatherInteractor
import com.example.weather_forecast_new.domain.impl_use_cases.WeatherInteractorImpl
import com.example.weather_forecast_new.domain.storageInteractor.CityStorageInteractor
import com.example.weather_forecast_new.presentation.presenters.storagePresenter.CityStoragePresenter
import com.example.weather_forecast_new.presentation.util.CheckingInternetUtil
import com.example.weather_forecast_new.presentation.util.LocationManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val domainModule = module {

    single <WeatherInteractor> {
        WeatherInteractorImpl(weatherRepository = get())
    }

    factory <CityStoragePresenter> {
        CityStoragePresenter(
            shared = get(),
        )
    }

    factory <CityStorageInteractor> {
        CityStorageInteractor(
            sharedPrefs = get()
        )
    }

    factory <LocationManager> {
        LocationManager(context = androidContext(), cityStoragePresenter = get())
    }

    factory <Boolean> {
        CheckingInternetUtil().isNetworkAvailable(context = androidContext())
    }

}