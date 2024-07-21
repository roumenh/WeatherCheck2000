package com.example.weathercheck2000.data.model

import com.example.weathercheck2000.network.model.WeatherForecastDto

data class WeatherForecast(
    val dailyMinTemperatures: List<Double>,
    val dailyMaxTemperatures: List<Double>,
    val dailyWeatherCodes: List<WeatherCode>,
    val hourlyTemperatures: List<Double>,
    val hourlyWindSpeeds: List<Double>,
    val hourlyWeatherCodes: List<WeatherCode>
)

fun WeatherForecastDto.asWeatherForecast(): WeatherForecast {
    val hoursToTake = 24
    return WeatherForecast(
        dailyMaxTemperatures = this.daily.temperature2mMax,
        dailyMinTemperatures = this.daily.temperature2mMin,
        dailyWeatherCodes = this.daily.weatherCodes.mapNotNull { mapOfWeatherCodes[it.toString()] }
            .take(hoursToTake),
        hourlyTemperatures = this.hourly.temperature2m.take(hoursToTake),
        hourlyWindSpeeds = this.hourly.windSpeed10m.take(hoursToTake),
        hourlyWeatherCodes = this.hourly.weatherCodes.mapNotNull { mapOfWeatherCodes[it.toString()] }
            .take(hoursToTake)
    )
}