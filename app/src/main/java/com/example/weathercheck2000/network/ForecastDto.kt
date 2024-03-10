/*
replaced with CurrentWeather
 */

package com.example.weathercheck2000.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastDto(
    val daily: DailyDto,
    @Json(name = "daily_units")
    val dailyUnits: DailyUnitsDto,
    val elevation: Double,
    @Json(name = "generationtime_ms")
    val generationtimeMs: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    @Json(name = "timezone_abbreviation")
    val timezoneAbbreviation: String,
    @Json(name = "utc_offset_seconds")
    val utcOffsetSeconds: Int
) {
    @JsonClass(generateAdapter = true)
    data class DailyDto(
        @Json(name = "temperature_2m_max")
        val temperature2mMax: List<Double>,
        @Json(name = "temperature_2m_min")
        val temperature2mMin: List<Double>,
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
}