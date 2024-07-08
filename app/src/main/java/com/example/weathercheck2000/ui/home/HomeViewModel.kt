package com.example.weathercheck2000.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.repository.CitiesRepository
import com.example.weathercheck2000.data.repository.MeteoInfoRepository
import com.example.weathercheck2000.database.cities.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class HomeSingleCityState(
    val city: City,
    val currentTemperature: Double?,
    val forecastTemperatureLow: Double?,
    val forecastTemperatureHigh: Double?
)

data class HomeUiState(
    val citiesAndCurrentTemperatures: List<HomeSingleCityState>,
)

class HomeViewModel(
    private val repository: CitiesRepository,
    private val meteoInfoRepository: MeteoInfoRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(listOf()))
    val uiState = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {

        val listOfCitiesWithTemperatures: MutableList<HomeSingleCityState> = mutableListOf()

        viewModelScope.launch {
            repository.allCities.collect { result ->

                result.forEach { city ->
                    combine(
                        meteoInfoRepository.getCurrentWeatherForCity(city.lat, city.lon),
                        meteoInfoRepository.getForecastForCity(city.lat, city.lon)
                    ) { currentWeather, forecast ->
                        listOfCitiesWithTemperatures.add(
                            HomeSingleCityState(
                                city = city,
                                currentTemperature = currentWeather.temperature,
                                forecastTemperatureLow = forecast.todayMinTemperature,
                                forecastTemperatureHigh = forecast.todayMaxTemperature,
                            )
                        )
                        Log.d("HomeViewModel", "Adding city to list ${city.name}")
                    }.catch {
                        listOfCitiesWithTemperatures.add(
                            HomeSingleCityState(
                                city = city,
                                currentTemperature = null,
                                forecastTemperatureLow = null,
                                forecastTemperatureHigh = null
                            )
                        )
                        Log.e("HomeViewModel", "Error fetching data", it)
                    }.collect {}

                }

                _uiState.value =
                    HomeUiState(citiesAndCurrentTemperatures = listOfCitiesWithTemperatures)


                //TODO introduce some loading state!

            }
        }

    }

}