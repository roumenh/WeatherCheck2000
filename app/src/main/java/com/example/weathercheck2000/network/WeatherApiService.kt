package com.example.weathercheck2000.network

import com.example.weathercheck2000.network.model.CurrentWeatherConditionsDto
import com.example.weathercheck2000.network.model.WeatherForecastDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.open-meteo.com/v1/"
//private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    //.addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService{

    @GET("forecast/?daily=temperature_2m_max,temperature_2m_min&timezone=Europe%2FLondon")  // latitude={latitude}&longitude{longitude}&
    suspend fun getForecast(
        @Query("latitude") latitude : String,
        @Query("longitude") longitude : String
    ) : WeatherForecastDto

    // https://api.open-meteo.com/v1/forecast?latitude=38.7072&longitude=-9.1355&current_weather=true&timezone=Europe%2FLondon
    @GET("forecast/?current_weather=true&timezone=Europe%2FLondon")  // latitude={latitude}&longitude{longitude}&
    suspend fun getCurrentWeather(
        @Query("latitude") latitude : String,
        @Query("longitude") longitude : String
    ) : CurrentWeatherConditionsDto


}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

