package com.example.weathercheck2000.data.repository

import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherForecast
import com.example.weathercheck2000.data.model.asCurrentWeather
import com.example.weathercheck2000.data.model.asWeatherForecast
import com.example.weathercheck2000.network.WeatherApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MeteoInfoRepository {
    suspend fun getForecastForCity(lat: String, lon: String) : Flow<WeatherForecast>
    suspend fun getCurrentWeatherForCity(lat: String, lon: String) : Flow<CurrentWeather>
}


class MeteoInfoRepositoryImpl : MeteoInfoRepository {

    override suspend fun getForecastForCity(lat: String, lon: String) = flow {

        emit(WeatherApi.retrofitService.getForecast(lat, lon).asWeatherForecast())

    }

    override suspend fun getCurrentWeatherForCity(lat: String, lon: String) = flow {

        emit(WeatherApi.retrofitService.getCurrentWeather(lat, lon).asCurrentWeather())

    }

}