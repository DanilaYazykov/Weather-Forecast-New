package com.example.weather_forecast_new.presentation.presenters.app

import android.app.Application
import com.example.weather_forecast_new.domain.di.dataModule
import com.example.weather_forecast_new.domain.di.domainModule
import com.example.weather_forecast_new.domain.di.weatherViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    domainModule,
                    dataModule,
                    weatherViewModelModule
                )
            )
        }
    }

}