package com.example.weather_forecast_new.domain.di

import com.example.weather_forecast_new.presentation.presenters.weatherViewModel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherViewModelModule = module {
    viewModel {
        WeatherViewModel(
            weatherInteractor = get(),
            cityStorage = get(),
            locationManager = get(),
            internet = get()
      )
    }
}