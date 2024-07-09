package com.example.weathercheck2000.data.model

import com.example.weathercheck2000.R

// weather codes data class.
data class WeatherCode(val imageId: Int, val description: String, val robinImage: Int? = null)

//map of weather codes
val mapOfWeatherCodes : Map<String, WeatherCode> = mapOf(
    "0" to WeatherCode(R.drawable.code0,"Clear sky", R.drawable.rimg_0_clear_sky),
    "1" to WeatherCode(R.drawable.code1,"Mainly clear", R.drawable.rimg_1_mainly_clear),
    "2" to WeatherCode(R.drawable.code2,"Partly cloudly", R.drawable.rimg_2_partly_cloudly),
    "3" to WeatherCode(R.drawable.code3,"Overcast", R.drawable.rimg_3_overcast),
    "45" to WeatherCode(R.drawable.code45,"Fog", R.drawable.rimg_45_fog),
    "48" to WeatherCode(R.drawable.code48,"Depositing rime fog", R.drawable.rimg_48_rime_fog),
    "51" to WeatherCode(R.drawable.code51,"Light drizzle", R.drawable.rimg_51_53_55_drizzle),
    "53" to WeatherCode(R.drawable.code53,"Moderate drizzle", R.drawable.rimg_51_53_55_drizzle),
    "55" to WeatherCode(R.drawable.code55,"Dense drizzle", R.drawable.rimg_51_53_55_drizzle),
    "56" to WeatherCode(R.drawable.code51,"Light freezing drizzle", R.drawable.rimg_56_57_freezing_drizzle),
    "57" to WeatherCode(R.drawable.code55,"Dense freezing drizzle", R.drawable.rimg_56_57_freezing_drizzle),
    "61" to WeatherCode(R.drawable.code61,"Slight rain", R.drawable.rimg_61_slight_rain),
    "63" to WeatherCode(R.drawable.code63,"Moderate rain", R.drawable.rimg_63_moderate_rain),
    "65" to WeatherCode(R.drawable.code65,"Heavy rain", R.drawable.rimg_65_heavy_rain),
    "66" to WeatherCode(R.drawable.code61,"Light freezing rain", R.drawable.rimg_66_67_freezing_rain),
    "67" to WeatherCode(R.drawable.code65,"Dense freezing rain", R.drawable.rimg_66_67_freezing_rain),
    "71" to WeatherCode(R.drawable.code71,"Slight snow fall", R.drawable.rimg_71_slight_snow_fall),
    "73" to WeatherCode(R.drawable.code71,"Moderate snow fall", R.drawable.rimg_73_moderate_snow_fall),
    "75" to WeatherCode(R.drawable.code75,"Heavy snow fall", R.drawable.rimg_75_heavy_snow_fall),
    "77" to WeatherCode(R.drawable.code71,"Snow grains", R.drawable.rimg_77_snow_grains),
    "80" to WeatherCode(R.drawable.code61,"Slight rain showers", R.drawable.rimg_80_slight_rain_showers),
    "81" to WeatherCode(R.drawable.code63,"Moderate rain showers", R.drawable.rimg_81_82_rain_showers),
    "82" to WeatherCode(R.drawable.code82,"Violent rain showers", R.drawable.rimg_81_82_rain_showers),
    "85" to WeatherCode(R.drawable.code71,"Slight snow showers", R.drawable.rimg_85_86_snow_showers),
    "86" to WeatherCode(R.drawable.code75,"Heavy snow showers", R.drawable.rimg_85_86_snow_showers),
    "95" to WeatherCode(R.drawable.code95,"Thunderstorm", R.drawable.rimg_95_thunderstorm),
    "96" to WeatherCode(R.drawable.code96,"Thunderstorm with slight hail", R.drawable.rimg_96_99_thunderstorm_with_hail),
    "99" to WeatherCode(R.drawable.code99,"Thunderstorm with heavy hail", R.drawable.rimg_96_99_thunderstorm_with_hail),
    "X" to WeatherCode(R.drawable.unknown, "Undefined")
)