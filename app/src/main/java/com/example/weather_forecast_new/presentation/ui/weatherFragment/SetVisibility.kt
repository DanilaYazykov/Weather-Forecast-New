package com.example.weather_forecast_new.presentation.ui.weatherFragment

import android.view.View
import com.example.weather_forecast_new.databinding.FragmentBlankBinding

class SetVisibility(private val binding: FragmentBlankBinding) {

    private fun showViews(vararg views: View) = with(binding) {
        rcViewForecast.visibility = View.GONE
        rcViewHours.visibility = View.GONE
        iwProgressBar.visibility = View.GONE
        tvNoResult.visibility = View.GONE
        ivCleanTvNoResult.visibility = View.GONE
        tvNoResult.visibility = View.GONE
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }

    fun simpleVisibility(s: Int?) {
        when (s) {
            SHOW_PROGRESS_BAR -> showViews(binding.iwProgressBar)
            SHOW_NO_RESULT -> showViews(binding.tvNoResult, binding.ivCleanTvNoResult)
            SHOW_SUCCESS -> showViews(binding.rcViewForecast, binding.rcViewHours)
            SHOW_NOTHING -> showViews()
            else -> Unit
        }
    }

    companion object {
        const val SHOW_PROGRESS_BAR = 1
        const val SHOW_NO_RESULT = 2
        const val SHOW_SUCCESS = 3
        const val SHOW_NOTHING = 4
    }
}