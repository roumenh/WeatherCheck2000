package com.example.weathercheck2000.ui.cityDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherForecast
import com.example.weathercheck2000.data.repository.CitiesRepository
import com.example.weathercheck2000.data.repository.MeteoInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CityDetailUiState(
    val cityName: String,
    val forecast: WeatherForecast?,
    val current: CurrentWeather?,
)

class CityDetailViewModel (
    private val meteoInfoRepository: MeteoInfoRepository,
    private val citiesRepository: CitiesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CityDetailUiState("",null, null))
    val uiState = _uiState.asStateFlow()

    init {
        //fetchData()
    }

    fun fetchData(id: Int){
        viewModelScope.launch {

            citiesRepository.getCity(id).collect {

                _uiState.value = _uiState.value.copy(cityName = it.name)

                val forecast = meteoInfoRepository.getForecastForCity(it.lat,it.lon) //TODO , connect to actual city from the first screen
                _uiState.value = _uiState.value.copy(forecast = forecast)
                //TODO improve states...

                val current = meteoInfoRepository.getCurrentWeatherForCity(it.lat,it.lon) //TODO , connect to actual city from the first screen
                _uiState.value = _uiState.value.copy(current = current)
                //TODO improve states...

            }

        }
    }

}