package com.example.weathercheck2000.data.model

import com.example.weathercheck2000.network.model.WeatherForecastDto

data class WeatherForecast(
    val todayMinTemperature: Double,
    val todayMaxTemperature: Double,
    //todo add more
    //,...
)

fun WeatherForecastDto.asWeatherForecast() =
    WeatherForecast(
        todayMinTemperature = this.daily.temperature2mMin.first(),
        todayMaxTemperature = this.daily.temperature2mMax.first(),
    )