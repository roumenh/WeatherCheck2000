package com.example.weathercheck2000.data.model

import com.example.weathercheck2000.network.ForecastDto

data class MeteoInfo(
    val currentTemperature: Double,
    val todayMinTemperature: Double,
    val todayMaxTemperature: Double,
    //todo add more
    //,...
)

fun ForecastDto.asMeteoInfo() =
    MeteoInfo(
        currentTemperature = 0.0,
        todayMinTemperature = this.daily.temperature2mMin.first(),
        todayMaxTemperature = this.daily.temperature2mMax.first(),

    )