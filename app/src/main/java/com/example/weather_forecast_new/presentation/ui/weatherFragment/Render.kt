package com.example.weather_forecast_new.presentation.ui.weatherFragment

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.weather_forecast_new.R
import com.example.weather_forecast_new.databinding.FragmentBlankBinding
import com.example.weather_forecast_new.domain.models.WeatherModel

class Render {

    fun drawingFragment(weather: WeatherModel?, binding: FragmentBlankBinding, view: View, context: Context) {
        binding.apply {
            tvCity.text = weather?.city
            tvData.text = weather?.time
            val currentTemp = "${weather?.currentTemp}°C"
            tvCurrentTemperature.text = currentTemp
            val maxMinTemp = "${weather?.maxTemp}° / ${weather?.minTemp}°"
            tvMaxMin.text = maxMinTemp
            val precipitation = if ((weather?.dailyChanceOfRain?.toIntOrNull() ?: 0) > (weather?.dailyChanceOfSnow?.toIntOrNull() ?: 0)) {
                "${weather?.dailyChanceOfRain} %"
            } else {
                "${weather?.dailyChanceOfSnow} %"
            }
            tvPrecipitation.text = precipitation
            val pressure = "${(weather?.pressure)} ${context.getString(R.string.pressure)}"
            tvPressure.text = pressure
            val wind = "${weather?.wind} ${context.getString(R.string.wind_speed)}"
            tvWindSpeed.text = wind
            updateWeatherAnimation(weather = weather, weatherAnimView = iwWeatherAnim, currentWeatherView = iwCurrentWeather, contextView = view)
            iwProgressBar.visibility = View.GONE
        }
    }

    fun updateWeatherAnimation(weather: WeatherModel?, weatherAnimView: LottieAnimationView, currentWeatherView: ImageView, contextView: View) {
        when (weather?.dayOrNight) {
            "1" -> {
                weatherAnimView.visibility = View.VISIBLE
                currentWeatherView.visibility = View.GONE
                when (weather.imageCode) {
                    "1000" -> weatherAnimView.setAnimation(R.raw.weather_sunny)
                    "1003" -> weatherAnimView.setAnimation(R.raw.weather_partly_cloudy)
                    "1006", "1009" -> weatherAnimView.setAnimation(R.raw.weather_overcast_windy)
                    "1087", "1273", "1276" -> weatherAnimView.setAnimation(R.raw.weather_thunder)
                    "1063", "1150", "1153", "1168", "1171", "1180", "1183", "1186", "1189", "1192",
                    "1195", "1198", "1201", "1204", "1207", "1240", "1243", "1246", "1249"
                    -> weatherAnimView.setAnimation(R.raw.weather_partly_shower)

                    "1282" -> weatherAnimView.setAnimation(R.raw.weather_storm)
                    "1066", "1069", "1210", "1216", "1222", "1252", "1255", "1258",
                    "1261", "1264" -> weatherAnimView.setAnimation(R.raw.weather_snow_sunny)

                    "1072", "1114", "1117", "1213", "1237", "1219", "1225"
                    -> weatherAnimView.setAnimation(R.raw.weather_snow)

                    "1279" -> weatherAnimView.setAnimation(R.raw.weather_stormshowersday)
                    "1030" -> weatherAnimView.setAnimation(R.raw.weather_foggy)
                    "1135", "1147" -> weatherAnimView.setAnimation(R.raw.weather_mist)
                }
            }
            "0" -> {
                weatherAnimView.visibility = View.VISIBLE
                currentWeatherView.visibility = View.GONE
                when (weather.imageCode) {
                    "1000" -> weatherAnimView.setAnimation(R.raw.weather_night)
                    "1003", "1006" -> weatherAnimView.setAnimation(R.raw.weather_cloudynight)
                    "1009" -> weatherAnimView.setAnimation(R.raw.weather_overcast_windy)
                    "1087", "1273", "1276" -> weatherAnimView.setAnimation(R.raw.weather_thunder)
                    "1063", "1150", "1153", "1168", "1171", "1180", "1183", "1186", "1189", "1192",
                    "1195", "1198", "1201", "1204", "1207", "1240", "1243", "1246", "1249"
                    -> weatherAnimView.setAnimation(R.raw.weather_rainynight)

                    "1282", "1279" -> weatherAnimView.setAnimation(R.raw.weather_storm)
                    "1066", "1069", "1210", "1216", "1222", "1252", "1255", "1258",
                    "1261", "1264" -> weatherAnimView.setAnimation(R.raw.weather_snownight)

                    "1072", "1114", "1117", "1213", "1237", "1219", "1225"
                    -> weatherAnimView.setAnimation(R.raw.weather_snow)

                    "1030", "1135", "1147" -> weatherAnimView.setAnimation(R.raw.weather_mist)
                }
            }
            else -> {
                weatherAnimView.visibility = View.GONE
                currentWeatherView.visibility = View.VISIBLE
                Glide.with(contextView)
                    .load("https:" + weather?.imageURL)
                    .centerCrop()
                    .into(currentWeatherView)
            }
        }
        weatherAnimView.playAnimation()
    }
}