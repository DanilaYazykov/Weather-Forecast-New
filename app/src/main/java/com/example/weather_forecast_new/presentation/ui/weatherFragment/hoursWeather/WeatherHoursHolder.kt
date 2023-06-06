package com.example.weather_forecast_new.presentation.ui.weatherFragment.hoursWeather

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_forecast_new.databinding.HoursBlankBinding
import com.example.weather_forecast_new.domain.models.WeatherModel
import com.example.weather_forecast_new.presentation.ui.weatherFragment.Render


class WeatherHoursHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = HoursBlankBinding.bind(itemView)

    fun bind(item: WeatherModel) = with(binding) {
        val date = item.time.split(" ")[1]
        tvTime.text = date
        val temp = "${item.currentTemp}°C"
        tvCurrentTemp1.text = temp
        Render().updateWeatherAnimation (weather = item, weatherAnimView = ivWeatherAnim, currentWeatherView = iwCurrentWeather, contextView = itemView)
    }
}