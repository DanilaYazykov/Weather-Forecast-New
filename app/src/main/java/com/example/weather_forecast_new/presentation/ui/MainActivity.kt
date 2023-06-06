package com.example.weather_forecast_new.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather_forecast_new.R
import com.example.weather_forecast_new.presentation.ui.weatherFragment.WeatherFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.place_holder, WeatherFragment.newInstance())
            .commit()

    }
}