package com.example.weathercheck2000.ui.cityDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherForecast
import com.example.weathercheck2000.data.repository.CitiesRepository
import com.example.weathercheck2000.data.repository.MeteoInfoRepository
import com.example.weathercheck2000.database.cities.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
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

    private val _allCities = MutableStateFlow<List<City>>(emptyList())
    var allCities = _allCities.asStateFlow()

    init {
        getAllCities()
    }

    private fun getAllCities() {
        viewModelScope.launch {
            citiesRepository.allCities.collectLatest {
                _allCities.value = it
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchWeatherDataForCity(id: Int){
        viewModelScope.launch {

            Log.e("TAG","Log 1 " + id.toString())

            citiesRepository.getCity(id).flowOn(Dispatchers.IO)
                .catch {
                    Log.e("CityDetailViewModel", "Error fetching data", it)
                    _uiState.value = CityDetailUiState.Error
                }
                .collect { it ->

                    Log.e("TAG","Log 2")
                    _uiState.value = CityDetailUiState.Loading
                    //emitAll(
                        combine(
                            meteoInfoRepository.getForecastForCity(it.lat, it.lon)
                                .map { Result.success(it) }
                                .catch { emit(Result.failure(it)) },
                            meteoInfoRepository.getCurrentWeatherForCity(it.lat, it.lon)
                                .map { Result.success(it) }
                                .catch { emit(Result.failure(it)) },
                        ) { forecast, currentWeather ->
                            Log.e("TAG","Log 3")
                            _uiState.value = CityDetailUiState.Success(
                                cityName = it.name,
                                forecast = forecast.getOrNull(),
                                current = currentWeather.getOrNull(),
                            )
                        }.collect{}
                 //   )
                }
            }/*.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CityDetailUiState.Loading
            )*/
        }


}


