package com.example.weather_forecast_new.presentation.ui.weatherFragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_forecast_new.R
import com.example.weather_forecast_new.databinding.FragmentBlankBinding
import com.example.weather_forecast_new.presentation.util.LocationManager
import com.example.weather_forecast_new.domain.models.WeatherModel
import com.example.weather_forecast_new.domain.models.WeatherResult
import com.example.weather_forecast_new.presentation.presenters.weatherViewModel.WeatherViewModel
import com.example.weather_forecast_new.presentation.presenters.storagePresenter.CityStoragePresenter
import com.example.weather_forecast_new.presentation.ui.weatherFragment.daysWeather.WeatherDaysAdapter
import com.example.weather_forecast_new.presentation.ui.weatherFragment.hoursWeather.WeatherHoursAdapter
import com.example.weather_forecast_new.presentation.util.DialogManagerUtil
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {

    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentBlankBinding
    private lateinit var adapterHours: WeatherHoursAdapter
    private lateinit var adapterDays: WeatherDaysAdapter
    private lateinit var visibility: SetVisibility

    private val cityStoragePresenter: CityStoragePresenter by inject()
    private val viewModel by viewModel <WeatherViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adaptersInit()

        var previousSuccessResult: WeatherResult.Success? = null
        visibility = SetVisibility(binding)
        viewModel.liveDataWeather.observe(viewLifecycleOwner) { weatherViewState ->
            weatherViewState.currentWeather?.let {
                when (weatherViewState.currentWeather) {
                    is WeatherResult.Success -> {
                        visibility.simpleVisibility(SetVisibility.SHOW_SUCCESS)
                        if (weatherViewState.currentWeather != previousSuccessResult) {
                            previousSuccessResult = weatherViewState.currentWeather
                            drawWeatherOnScreen(weatherViewState.currentWeather.weatherModel)
                            val time = weatherViewState.currentWeather.weatherModel.time.split(" ")[1].split(":")[0].toInt()
                            view.postDelayed({ binding.rcViewHours.scrollToPosition(time) }, 100)
                        }
                    }
                    is WeatherResult.Error -> {
                        binding.tvNoResult.setText(R.string.something_wrong)
                        visibility.simpleVisibility(SetVisibility.SHOW_NO_RESULT)
                    }
                    is WeatherResult.NoData -> {
                        binding.tvNoResult.setText(R.string.no_result)
                        visibility.simpleVisibility(SetVisibility.SHOW_NO_RESULT)
                    }
                    is WeatherResult.SavedData -> viewModel.getPreviousWeather()
                }
            }

            weatherViewState.forecastHoursWeather.let { hoursWeather ->
                if (hoursWeather.isNotEmpty()) {
                    adapterHours.submitList(hoursWeather)
                }
            }

            weatherViewState.forecastDaysWeather.let { daysWeather ->
                if (daysWeather.isNotEmpty()) {
                    adapterDays.submitList(daysWeather)
                }
            }
        }

        viewModel.liveDataInternetGps.distinctUntilChanged().observe(viewLifecycleOwner) { weatherViewState ->
            if (!weatherViewState.gpsData) {
                DialogManagerUtil().locationSettingsDialog(
                    requireContext(),
                    object : DialogManagerUtil.Listener {
                        override fun onClick(name: String?) {
                            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        }
                    })
            }

            if (!weatherViewState.internetData) {
                DialogManagerUtil().internetSettingsDialog(
                    requireContext(), object : DialogManagerUtil.Listener {
                        override fun onClick(name: String?) {
                            visibility.simpleVisibility(SetVisibility.SHOW_NOTHING)
                            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                        }
                    })
            }
        }

        setUpOnClickListeners()
        checkPermission()
    }

    private fun setUpOnClickListeners() {
        binding.ibSearch.setOnClickListener {
            DialogManagerUtil().searchByNameDialog(
                requireContext(),
                object : DialogManagerUtil.Listener {
                    override fun onClick(name: String?) {
                        visibility.simpleVisibility(SetVisibility.SHOW_PROGRESS_BAR)
                        viewModel.loadWeather(name!!)
                    }
                })
        }

        binding.ibReload.setOnClickListener {
            visibility.simpleVisibility(SetVisibility.SHOW_PROGRESS_BAR)
            viewModel.getLocation()
        }

        binding.ivCleanTvNoResult.setOnClickListener {
            visibility.simpleVisibility(SetVisibility.SHOW_NOTHING)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocation()
    }

    private fun adaptersInit() = with(binding) {
        rcViewHours.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapterHours = WeatherHoursAdapter()
        rcViewHours.adapter = adapterHours

        rcViewForecast.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapterDays = WeatherDaysAdapter()
        rcViewForecast.adapter = adapterDays
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted ->
            if (isGranted) visibility.simpleVisibility(SetVisibility.SHOW_PROGRESS_BAR)
        }
    }

    private fun checkPermission() {
        if (!LocationManager(requireContext(), cityStoragePresenter = cityStoragePresenter).isPermissionGranted(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun drawWeatherOnScreen(weather: WeatherModel?) {
        val draw = Render()
        draw.drawingFragment(
            weather = weather,
            binding = binding,
            view = requireView(),
            context = requireContext()
        )
    }
}