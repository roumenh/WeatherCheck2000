package com.example.weathercheck2000.ui.cityDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherForecast
import com.example.weathercheck2000.data.repository.CitiesRepository
import com.example.weathercheck2000.data.repository.MeteoInfoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed class CityDetailUiState {

    object Loading : CityDetailUiState()

    object Error : CityDetailUiState()

    data class Success(
        val cityName: String,
        val forecast: WeatherForecast? = null,
        val current: CurrentWeather? = null,
    ) : CityDetailUiState()
}

class CityDetailViewModel(
    private val meteoInfoRepository: MeteoInfoRepository,
    private val citiesRepository: CitiesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CityDetailUiState>(CityDetailUiState.Loading)
    var uiState = _uiState.asStateFlow()

    init {
        //fetchData()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchData(id: Int) {
        viewModelScope.launch {

            uiState = citiesRepository.getCity(id).flatMapLatest {
                flow {
                    emit(CityDetailUiState.Loading)
                    emitAll(
                        combine(
                            meteoInfoRepository.getForecastForCity(it.lat, it.lon)
                                .map { Result.success(it) }
                                .catch { emit(Result.failure(it)) },
                            meteoInfoRepository.getCurrentWeatherForCity(it.lat, it.lon)
                                .map { Result.success(it) }
                                .catch { emit(Result.failure(it)) },
                        ) { forecast, currentWeather ->
                            CityDetailUiState.Success(
                                cityName = it.name,
                                forecast = forecast.getOrNull(),
                                current = currentWeather.getOrNull(),
                            )
                        }
                    )
                }
            }.catch {
                Log.e("CityDetailViewModel", "Error fetching data", it)
                emit(CityDetailUiState.Error)
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = CityDetailUiState.Loading
                )
        }
        /*                _uiState.value = CityDetailUiState.Success(cityName = it.name)

                        val forecast = meteoInfoRepository.getForecastForCity(it.lat,it.lon) //TODO , connect to actual city from the first screen
                        _uiState.value = _uiState.value.copy(forecast = forecast)
                        //TODO improve states...

                        val current = meteoInfoRepository.getCurrentWeatherForCity(it.lat,it.lon) //TODO , connect to actual city from the first screen
                        _uiState.value = _uiState.value.copy(current = current)
                        //TODO improve states...*/

    }

}


