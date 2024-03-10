package com.example.weathercheck2000.data.repository

import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherForecast
import com.example.weathercheck2000.data.model.asCurrentWeather
import com.example.weathercheck2000.data.model.asWeatherForecast
import com.example.weathercheck2000.network.WeatherApi

class MeteoInfoRepository {

    suspend fun getForecastForCity(lat: String, lon: String) : WeatherForecast {

        return WeatherApi.retrofitService.getForecast(lat, lon).asWeatherForecast()

    }

    suspend fun getCurrentWeatherForCity(lat: String, lon: String) : CurrentWeather {

        return WeatherApi.retrofitService.getCurrentWeather(lat, lon).asCurrentWeather()

    }

}