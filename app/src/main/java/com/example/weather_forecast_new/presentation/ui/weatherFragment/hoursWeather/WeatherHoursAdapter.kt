package com.example.weather_forecast_new.presentation.ui.weatherFragment.hoursWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.weather_forecast_new.R
import com.example.weather_forecast_new.domain.models.WeatherModel
import com.example.weather_forecast_new.presentation.ui.weatherFragment.daysWeather.Comparator

class WeatherHoursAdapter : ListAdapter<WeatherModel, WeatherHoursHolder>(Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHoursHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hours_blank, parent, false)
        return WeatherHoursHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHoursHolder, position: Int) {
        holder.bind(getItem(position))
    }
}