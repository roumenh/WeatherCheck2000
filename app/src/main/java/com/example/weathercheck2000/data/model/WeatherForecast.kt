package com.example.weathercheck2000.data.model

import com.example.weathercheck2000.network.model.WeatherForecastDto

data class WeatherForecast(
    val todayMinTemperature: Double, //TODO deprecate
    val todayMaxTemperature: Double, //TODO deprecate
    val futureMinTemperatures: List<Double>,
    val futureMaxTemperatures: List<Double>,
    val weatherCodes: List<WeatherCode>,
    //todo add more
    //,...
)

fun WeatherForecastDto.asWeatherForecast() =
    WeatherForecast(
        todayMinTemperature = this.daily.temperature2mMin.first(), //TODO deprecate
        todayMaxTemperature = this.daily.temperature2mMax.first(), //TODO deprecate
        futureMaxTemperatures = this.daily.temperature2mMax,
        futureMinTemperatures = this.daily.temperature2mMin,
        weatherCodes = this.daily.weatherCodes.mapNotNull { mapOfWeatherCodes[it.toString()] },
    )