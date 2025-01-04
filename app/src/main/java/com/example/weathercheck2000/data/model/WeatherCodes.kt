package com.example.weathercheck2000.data.model

import androidx.annotation.StringRes
import com.example.weathercheck2000.R

// weather codes data class.
data class WeatherCode(
    val imageId: Int,
    @StringRes val description: Int,
    val robinImage: Int? = null,
    val code: Int,
)

//map of weather codes
val mapOfWeatherCodes: Map<String, WeatherCode> = mapOf(
    "0" to WeatherCode(R.drawable.code0, R.string.clear_sky, R.drawable.rimg_0_clear_sky, 0),
    "1" to WeatherCode(R.drawable.code1, R.string.mainly_clear, R.drawable.rimg_1_mainly_clear, 1),
    "2" to WeatherCode(R.drawable.code2, R.string.partly_cloudly, R.drawable.rimg_2_partly_cloudly, 2),
    "3" to WeatherCode(R.drawable.code3, R.string.overcast, R.drawable.rimg_3_overcast, 3),
    "45" to WeatherCode(R.drawable.code45, R.string.fog, R.drawable.rimg_45_fog, 45),
    "48" to WeatherCode(R.drawable.code48, R.string.depositing_rime_fog, R.drawable.rimg_48_rime_fog, 48),
    "51" to WeatherCode(R.drawable.code51, R.string.light_drizzle, R.drawable.rimg_51_53_55_drizzle, 51),
    "53" to WeatherCode(R.drawable.code53, R.string.moderate_drizzle, R.drawable.rimg_51_53_55_drizzle, 51),
    "55" to WeatherCode(R.drawable.code55, R.string.dense_drizzle, R.drawable.rimg_51_53_55_drizzle, 51),
    "56" to WeatherCode(R.drawable.code51, R.string.light_freezing_drizzle, R.drawable.rimg_56_57_freezing_drizzle, 56),
    "57" to WeatherCode(R.drawable.code55, R.string.dense_freezing_drizzle, R.drawable.rimg_56_57_freezing_drizzle, 56),
    "61" to WeatherCode(R.drawable.code61, R.string.slight_rain, R.drawable.rimg_61_slight_rain, 61),
    "63" to WeatherCode(R.drawable.code63, R.string.moderate_rain, R.drawable.rimg_63_moderate_rain, 63),
    "65" to WeatherCode(R.drawable.code65, R.string.heavy_rain, R.drawable.rimg_65_heavy_rain, 65),
    "66" to WeatherCode(R.drawable.code61, R.string.light_freezing_rain, R.drawable.rimg_66_67_freezing_rain, 66),
    "67" to WeatherCode(R.drawable.code65, R.string.dense_freezing_rain, R.drawable.rimg_66_67_freezing_rain, 66),
    "71" to WeatherCode(R.drawable.code71, R.string.slight_snow_fall, R.drawable.rimg_71_slight_snow_fall, 71),
    "73" to WeatherCode(R.drawable.code71, R.string.moderate_snow_fall, R.drawable.rimg_73_moderate_snow_fall, 73),
    "75" to WeatherCode(R.drawable.code75, R.string.heavy_snow_fall, R.drawable.rimg_75_heavy_snow_fall, 75),
    "77" to WeatherCode(R.drawable.code71, R.string.snow_grains, R.drawable.rimg_77_snow_grains, 77),
    "80" to WeatherCode(R.drawable.code61, R.string.slight_rain_showers, R.drawable.rimg_80_slight_rain_showers, 80),
    "81" to WeatherCode(R.drawable.code63, R.string.moderate_rain_showers, R.drawable.rimg_81_82_rain_showers, 81),
    "82" to WeatherCode(R.drawable.code82, R.string.violent_rain_showers, R.drawable.rimg_81_82_rain_showers, 81),
    "85" to WeatherCode(R.drawable.code71, R.string.slight_snow_showers, R.drawable.rimg_85_86_snow_showers, 85),
    "86" to WeatherCode(R.drawable.code75, R.string.heavy_snow_showers, R.drawable.rimg_85_86_snow_showers, 85),
    "95" to WeatherCode(R.drawable.code95, R.string.thunderstorm, R.drawable.rimg_95_thunderstorm, 95),
    "96" to WeatherCode(R.drawable.code96, R.string.thunderstorm_with_slight_hail, R.drawable.rimg_96_99_thunderstorm_with_hail, 96),
    "99" to WeatherCode(R.drawable.code99, R.string.thunderstorm_with_heavy_hail, R.drawable.rimg_96_99_thunderstorm_with_hail, 96),
    "X" to WeatherCode(R.drawable.unknown, R.string.undefined, null, 0)
)