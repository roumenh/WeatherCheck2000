/*
replaced with CurrentWeather
 */

package com.example.weathercheck2000.network.model

import com.example.weathercheck2000.data.model.WeatherCode
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecastDto(
    val daily: DailyDto,
    @Json(name = "daily_units")
    val dailyUnits: DailyUnitsDto, //do we even need dailyUnits for something? As well as we do not need hourlyUnits?
    val elevation: Double,
    @Json(name = "generationtime_ms")
    val generationTimeMs: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    @Json(name = "timezone_abbreviation")
    val timezoneAbbreviation: String,
    @Json(name = "utc_offset_seconds")
    val utcOffsetSeconds: Int,
    val hourly: HourlyDto,
) {
    @JsonClass(generateAdapter = true)
    data class DailyDto(
        @Json(name = "temperature_2m_max")
        val temperature2mMax: List<Double>,
        @Json(name = "temperature_2m_min")
        val temperature2mMin: List<Double>,
        @Json(name = "weather_code")
        val weatherCodes: List<Int>,
        val time: List<String>
    )

    @JsonClass(generateAdapter = true)
    data class DailyUnitsDto(
        @Json(name = "temperature_2m_max")
        val temperature2mMax: String,
        @Json(name = "temperature_2m_min")
        val temperature2mMin: String,
        val time: String
    )

    @JsonClass(generateAdapter = true)
    data class HourlyDto(
        val time: List<String>,
        @Json(name = "temperature_2m")
        val temperature2m: List<Double>,
        @Json(name = "weather_code")
        val weatherCodes: List<Int>,
        @Json(name = "wind_speed_10m")
        val windSpeed10m: List<Double>
    )
}