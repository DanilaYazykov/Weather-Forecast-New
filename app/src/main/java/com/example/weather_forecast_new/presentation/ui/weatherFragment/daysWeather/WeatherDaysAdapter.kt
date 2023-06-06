package com.example.weather_forecast_new.presentation.ui.weatherFragment.daysWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.weather_forecast_new.R
import com.example.weather_forecast_new.domain.models.WeatherModel

class WeatherDaysAdapter : ListAdapter<WeatherModel, WeatherDaysHolder>(Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDaysHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.days_blank, parent, false)
        return WeatherDaysHolder(view, context = parent.context)
    }

    override fun onBindViewHolder(holder: WeatherDaysHolder, position: Int) {
        holder.bind(getItem(position))
    }
}