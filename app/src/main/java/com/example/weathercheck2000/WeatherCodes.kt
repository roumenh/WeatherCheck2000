package com.example.weathercheck2000

// weather codes data class.
data class WeatherCode(val imageId: Int, val description: String)

//map of weather codes
val mapOfWeatherCodes : Map<String,WeatherCode> = mapOf(
    "0" to WeatherCode(R.drawable.code0,"Clear sky"),
    "1" to WeatherCode(R.drawable.code1,"Mainly clear"),
    "2" to WeatherCode(R.drawable.code2,"Partly cloudly"),
    "3" to WeatherCode(R.drawable.code3,"Overcast"),
    "X" to WeatherCode(R.drawable.unknown, "Undefined")
)