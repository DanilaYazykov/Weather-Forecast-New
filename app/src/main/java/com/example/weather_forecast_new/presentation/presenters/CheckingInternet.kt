package com.example.weather_forecast_new.presentation.presenters

import android.content.Context

interface CheckingInternet {
    fun isNetworkAvailable(context: Context?): Boolean
}