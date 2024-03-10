package com.example.weathercheck2000.data.repository

import com.example.weathercheck2000.data.model.MeteoInfo
import com.example.weathercheck2000.data.model.asMeteoInfo
import com.example.weathercheck2000.network.ForecastDto
import com.example.weathercheck2000.network.WeatherApi

class MeteoInfoRepository {

    suspend fun getMeteoInfoForCity(lat: String, lon: String) : MeteoInfo {

        return WeatherApi.retrofitService.getForecast(lat, lon).asMeteoInfo()

    }

}