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

class CityDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CityDetailUiState(null, null))
    val uiState = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch {

            //TODO introduce DI for repository
            val forecast = MeteoInfoRepository().getForecastForCity("25.5","25.0") //TODO , connect to actual city from the first screen
            _uiState.value = _uiState.value.copy(forecast = forecast)
            //TODO improve states...

            //TODO introduce DI for repository
            val current = MeteoInfoRepository().getCurrentWeatherForCity("25.5","25.0") //TODO , connect to actual city from the first screen
            _uiState.value = _uiState.value.copy(current = current)
            //TODO improve states...

        }
    }

}