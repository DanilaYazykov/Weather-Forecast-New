package com.example.weather_forecast_new.presentation.ui.weatherFragment.daysWeather

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_forecast_new.R
import com.example.weather_forecast_new.databinding.DaysBlankBinding
import com.example.weather_forecast_new.domain.models.WeatherModel
import com.example.weather_forecast_new.presentation.ui.weatherFragment.Render
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class WeatherDaysHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
    private val binding = DaysBlankBinding.bind(itemView)

    fun bind(item: WeatherModel) = with(binding) {
        val time = dayOfWeek(item.time)
        tvDay.text = time

        val maxMinTemp = "${item.maxTemp}° / ${item.minTemp}°"
        tvMaxMinTemp.text = maxMinTemp
        tvSunrise.text = item.sunrise.split(" ")[0]
        val europeanTime = calculateSunset(item.sunset)
        tvSunset.text = europeanTime

        val precipitation = if ((item.dailyChanceOfRain?.toIntOrNull() ?: 0) > (item.dailyChanceOfSnow?.toIntOrNull() ?: 0)) {
            "${item.dailyChanceOfRain} %"
        } else {
            "${item.dailyChanceOfSnow} %"
        }
        tvPrecipitation.text = precipitation
        Render().updateWeatherAnimation(
            weather = item,
            weatherAnimView = ivWeatherAnim,
            currentWeatherView = iwCurrentWeather,
            contextView = itemView
        )
    }

    private fun dayOfWeek(item: String): String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(item)
        val calendar = Calendar.getInstance().apply { if (date != null) time = date }

        val today = Calendar.getInstance()
        val isToday = (today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && today.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR))

        val time = if (isToday) {
            context.getString(R.string.Today)
        } else {
            val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)
            dayOfWeek.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }

        return time
    }

    private fun calculateSunset(item: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        val localTime = LocalTime.parse(item, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return localTime.format(outputFormatter)
    }
}