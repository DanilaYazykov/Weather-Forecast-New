package com.example.weather_forecast_new.data.network

import com.example.weather_forecast_new.domain.models.WeatherModel
import com.google.gson.JsonObject
import java.math.RoundingMode
import kotlin.math.roundToInt

class ParseWeatherData {

    fun parseWeatherData(responseBody: JsonObject): WeatherModel {
        val list = parseDays(responseBody)
        return parseCurrentData(responseBody, list[0])
    }

    private fun parseDays(responseBody: JsonObject): List<WeatherModel> {
        val name = responseBody.getAsJsonObject("location").get("name").asString
        val daysArray = responseBody.getAsJsonObject("forecast").getAsJsonArray("forecastday")

        return daysArray.map { dayElement ->
            val day = dayElement.asJsonObject
            val hoursArray = day.getAsJsonArray("hour")

            val listHours = hoursArray.map { hourElement ->
                val hour = hourElement.asJsonObject
                WeatherModel(
                    time = hour.get("time").asString,
                    imageURL = hour.getAsJsonObject("condition").get("icon").asString,
                    imageCode = hour.getAsJsonObject("condition").get("code").asString,
                    currentTemp = hour.get("temp_c").asFloat.roundToInt().toString(),
                    dayOrNight = hour.get("is_day").asString,
                    city = "",
                    condition = "",
                    maxTemp = "",
                    minTemp = "",
                    sunrise = "",
                    sunset = "",
                    dailyChanceOfRain = "",
                    dailyChanceOfSnow = "",
                    hours = arrayListOf(),
                    forecastDays = arrayListOf(),
                    currentTempFeelsLike = "",
                    pressure = "",
                    wind = ""
                )
            }.toMutableList()

            val forecastDays = daysArray.map { dayElements ->
                val forecastDay = dayElements.asJsonObject
                WeatherModel(
                    time = forecastDay.get("date").asString,
                    condition = forecastDay.getAsJsonObject("day").getAsJsonObject("condition")
                        .get("text").asString,
                    imageURL = forecastDay.getAsJsonObject("day").getAsJsonObject("condition")
                        .get("icon").asString,
                    imageCode = forecastDay.getAsJsonObject("day").getAsJsonObject("condition")
                        .get("code").asString,
                    maxTemp = forecastDay.getAsJsonObject("day").get("maxtemp_c").asFloat.roundToInt().toString(),
                    minTemp = forecastDay.getAsJsonObject("day").get("mintemp_c").asFloat.roundToInt().toString(),
                    sunrise = forecastDay.getAsJsonObject("astro").get("sunrise").asString,
                    sunset = forecastDay.getAsJsonObject("astro").get("sunset").asString,
                    dailyChanceOfRain = forecastDay.getAsJsonObject("day").get("daily_chance_of_rain").asString,
                    dailyChanceOfSnow = forecastDay.getAsJsonObject("day").get("daily_chance_of_snow").asString,
                    dayOrNight = "1",
                    city = "",
                    hours = arrayListOf(),
                    forecastDays = arrayListOf(),
                    currentTempFeelsLike = "",
                    pressure = "",
                    wind = "",
                    currentTemp = ""
                )
            }.toMutableList()

            WeatherModel(
                city = name,
                time = day.get("date").asString,
                condition = day.getAsJsonObject("day").getAsJsonObject("condition")
                    .get("text").asString,
                imageURL = day.getAsJsonObject("day").getAsJsonObject("condition")
                    .get("icon").asString,
                imageCode = day.getAsJsonObject("day").getAsJsonObject("condition")
                    .get("code").asString,
                maxTemp = day.getAsJsonObject("day").get("maxtemp_c").asFloat.roundToInt().toString(),
                minTemp = day.getAsJsonObject("day").get("mintemp_c").asFloat.roundToInt().toString(),
                sunrise = day.getAsJsonObject("astro").get("sunrise").asString,
                sunset = day.getAsJsonObject("astro").get("sunset").asString,
                dailyChanceOfRain = day.getAsJsonObject("day").get("daily_chance_of_rain").asString,
                dailyChanceOfSnow = day.getAsJsonObject("day").get("daily_chance_of_snow").asString,
                hours = listHours,
                forecastDays = forecastDays,
                currentTempFeelsLike = "",
                pressure = "",
                wind = "",
                currentTemp = "",
                dayOrNight = ""
            )
        }
    }

    private fun parseCurrentData(
        responseBody: JsonObject,
        weatherItem: WeatherModel
    ): WeatherModel {
        val currentTemp = responseBody.getAsJsonObject("current").get("temp_c").asFloat.roundToInt()
        val maxTemp = maxOf(weatherItem.maxTemp.toInt(), currentTemp)
        val minTemp = minOf(weatherItem.minTemp.toInt(), currentTemp)
        val wind = responseBody.getAsJsonObject("current").get("wind_kph").asBigDecimal.setScale(0, RoundingMode.HALF_UP).toInt()
        val pressure = responseBody.getAsJsonObject("current").get("pressure_mb").asInt * 0.75006376

        return WeatherModel(
            city = responseBody.getAsJsonObject("location").get("name").asString,
            time = responseBody.getAsJsonObject("location").get("localtime").asString,
            condition = responseBody.getAsJsonObject("current").getAsJsonObject("condition")
                .get("text").asString,
            imageURL = responseBody.getAsJsonObject("current").getAsJsonObject("condition")
                .get("icon").asString,
            imageCode = responseBody.getAsJsonObject("current").getAsJsonObject("condition")
                .get("code").asString,
            currentTemp = currentTemp.toString(),
            currentTempFeelsLike = responseBody.getAsJsonObject("current")
                .get("feelslike_c").asString,
            maxTemp = maxTemp.toString(),
            minTemp = minTemp.toString(),
            hours = weatherItem.hours,
            sunrise = weatherItem.sunrise,
            sunset = weatherItem.sunset,
            pressure = pressure.toInt().toString(),
            wind = wind.toString(),
            dailyChanceOfRain = weatherItem.dailyChanceOfRain,
            dailyChanceOfSnow = weatherItem.dailyChanceOfSnow,
            dayOrNight = responseBody.getAsJsonObject("current").get("is_day").asString,
            forecastDays = weatherItem.forecastDays
        )
    }
}