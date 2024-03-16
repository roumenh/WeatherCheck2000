package com.example.weathercheck2000.ui.cityDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherForecast
import com.example.weathercheck2000.data.repository.MeteoInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CityDetailUiState(
    val forecast: WeatherForecast?,
    val current: CurrentWeather?,
)

class CityDetailViewModel (
    private val meteoInfoRepository: MeteoInfoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CityDetailUiState(null, null))
    val uiState = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch {

            val forecast = meteoInfoRepository.getForecastForCity("25.5","25.0") //TODO , connect to actual city from the first screen
            _uiState.value = _uiState.value.copy(forecast = forecast)
            //TODO improve states...

            val current = meteoInfoRepository.getCurrentWeatherForCity("25.5","25.0") //TODO , connect to actual city from the first screen
            _uiState.value = _uiState.value.copy(current = current)
            //TODO improve states...

        }
    }

}