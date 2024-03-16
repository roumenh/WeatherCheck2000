package com.example.weathercheck2000.data.model

import com.example.weathercheck2000.R

// weather codes data class.
data class WeatherCode(val imageId: Int, val description: String)

//map of weather codes
val mapOfWeatherCodes : Map<String, WeatherCode> = mapOf(
    "0" to WeatherCode(R.drawable.code0,"Clear sky"),
    "1" to WeatherCode(R.drawable.code1,"Mainly clear"),
    "2" to WeatherCode(R.drawable.code2,"Partly cloudly"),
    "3" to WeatherCode(R.drawable.code3,"Overcast"),
    "45" to WeatherCode(R.drawable.code45,"Fog"),
    "48" to WeatherCode(R.drawable.code48,"Depositing rime fog"),
    "51" to WeatherCode(R.drawable.code51,"Light drizzle"),
    "53" to WeatherCode(R.drawable.code53,"Moderate drizzle"),
    "55" to WeatherCode(R.drawable.code55,"Dense drizzle"),
    "56" to WeatherCode(R.drawable.code51,"Light freezing drizzle"),
    "57" to WeatherCode(R.drawable.code55,"Dense freezing drizzle"),
    "61" to WeatherCode(R.drawable.code61,"Slight rain"),
    "63" to WeatherCode(R.drawable.code63,"Moderate rain"),
    "65" to WeatherCode(R.drawable.code65,"Heavy rain"),
    "66" to WeatherCode(R.drawable.code61,"Light freezing rain"),
    "67" to WeatherCode(R.drawable.code65,"Dense freezing rain"),
    "71" to WeatherCode(R.drawable.code71,"Slight snow fall"),
    "73" to WeatherCode(R.drawable.code71,"Moderate snow fall"),
    "75" to WeatherCode(R.drawable.code75,"Heavy snow fall"),
    "77" to WeatherCode(R.drawable.code71,"Snow grains"),
    "80" to WeatherCode(R.drawable.code61,"Slight rain showers"),
    "81" to WeatherCode(R.drawable.code63,"Moderate rain showers"),
    "82" to WeatherCode(R.drawable.code82,"Violent rain showers"),
    "85" to WeatherCode(R.drawable.code71,"Slight snow showers"),
    "86" to WeatherCode(R.drawable.code75,"Heavy snow showers"),
    "95" to WeatherCode(R.drawable.code95,"Thunderstorm"),
    "96" to WeatherCode(R.drawable.code96,"Thunderstorm with slight hail"),
    "99" to WeatherCode(R.drawable.code99,"Thunderstorm with heavvy hail"),
    "X" to WeatherCode(R.drawable.unknown, "Undefined")
)