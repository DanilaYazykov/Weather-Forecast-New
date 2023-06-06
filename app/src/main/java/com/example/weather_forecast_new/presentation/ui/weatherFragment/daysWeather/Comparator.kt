package com.example.weather_forecast_new.presentation.ui.weatherFragment.daysWeather

import androidx.recyclerview.widget.DiffUtil
import com.example.weather_forecast_new.domain.models.WeatherModel

class Comparator: DiffUtil.ItemCallback<WeatherModel>() {
    override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem == newItem
    }
}