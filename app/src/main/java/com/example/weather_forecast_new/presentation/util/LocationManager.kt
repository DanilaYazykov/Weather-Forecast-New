package com.example.weather_forecast_new.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import com.google.android.gms.tasks.Tasks
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weather_forecast_new.presentation.presenters.storagePresenter.CityStoragePresenter
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class LocationManager(
    private val context: Context,
    private val cityStoragePresenter: CityStoragePresenter
) {

    fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getLocation(): String {
        val city = cityStoragePresenter.getSavedCity()
        if (!isLocationEnabled()) {
            return city.ifEmpty { CITYv1 }
        }
        val token = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return city.ifEmpty { CITYv2 }
        }
        val locationTask = LocationServices
            .getFusedLocationProviderClient(context)
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token.token)
        val location = Tasks.await(locationTask)
        val lat = location.latitude
        val lon = location.longitude
        return "$lat,$lon"
    }

    companion object {
        const val CITYv1 = "Lisbon"
        const val CITYv2 = "Minsk"
    }
}