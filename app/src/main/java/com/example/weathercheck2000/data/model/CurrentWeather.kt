package com.example.weathercheck2000.data.model

import com.example.weathercheck2000.WeatherCode
import com.example.weathercheck2000.mapOfWeatherCodes
import com.example.weathercheck2000.network.CurrentWeatherConditionsDto

data class CurrentWeather (
    val temperature: Double,
    val weatherCode: WeatherCode?,
    val windDirection: Double,
    val windSpeed: Double,
)

fun CurrentWeatherConditionsDto.asCurrentWeather() = CurrentWeather(
    temperature = this.currentWeather.temperature,
    weatherCode = mapOfWeatherCodes[this.currentWeather.weatherCode.toString()], //TODO why toString, if we are actually getting Int?
    windDirection = this.currentWeather.windDirection,
    windSpeed = this.currentWeather.windSpeed,
)

