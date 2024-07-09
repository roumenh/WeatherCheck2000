package com.example.weathercheck2000.ui.cityDetail

import com.example.weathercheck2000.R
import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherCode

fun robinImageResolver(currentWeather: CurrentWeather) : Int {

    /* general logic:
        - if the current time is after 21 o clock, show evening robin images, randomly
        - if the temeprature is above 26, show some "hot images"
        - otherwise, show based on weather code.
     */

    //return robin image
    currentWeather.weatherCode?.robinImage?.let {
        return it
    } ?: run {
        return R.drawable.img_placeholder //todo OR ERROR!
    }

}