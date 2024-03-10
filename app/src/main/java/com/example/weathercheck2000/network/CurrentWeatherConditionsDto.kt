package com.example.weathercheck2000.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeatherConditionsDto(
    @Json(name = "current_weather")
    val currentWeather: CurrentWeatherDto,
    val elevation: Double,
    @Json(name = "generationtime_ms")
    val generationtimeMs: Double,
    val latitude: Double,
    val longitude: Double,
    @Json(name = "timezone")
    val timezone: String,
    @Json(name = "timezone_abbreviation")
    val timezoneAbbreviation: String,
    @Json(name = "utc_offset_seconds")
    val utcOffsetSeconds: Int
) {
    @JsonClass(generateAdapter = true)
    data class CurrentWeatherDto(
        val temperature: Double,
        val time: String,
        @Json(name = "weathercode")
        val weatherCode: Int,
        @Json(name = "winddirection")
        val windDirection: Double,
        @Json(name = "windspeed")
        val windSpeed: Double
    )
}