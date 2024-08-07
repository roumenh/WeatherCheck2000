package com.example.weathercheck2000.ui.cityDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherForecast
import com.example.weathercheck2000.data.repository.CitiesRepository
import com.example.weathercheck2000.data.repository.CollectiblesRepository
import com.example.weathercheck2000.data.repository.MeteoInfoRepository
import com.example.weathercheck2000.database.cities.City
import com.example.weathercheck2000.database.collectibles.Collectible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch


sealed class CityDetailUiState {

    data object Loading : CityDetailUiState()

    data object Error : CityDetailUiState()

    data class Success(
        val city: City,
        val forecast: WeatherForecast? = null,
        val current: CurrentWeather? = null,
    ) : CityDetailUiState()
}


class CityDetailViewModel(
    private val meteoInfoRepository: MeteoInfoRepository,
    private val citiesRepository: CitiesRepository,
    private val collectiblesRepository: CollectiblesRepository
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

    @OptIn(FlowPreview::class)
    fun fetchWeatherDataForCity(id: Int) {

        Log.d("TAG", "Fetching data for city id $id")
        viewModelScope.launch {
            citiesRepository.getCity(id)
                .flowOn(Dispatchers.IO)
                .debounce(200)
                .catch {
                    Log.e("CityDetailViewModel", "Error fetching data", it)
                    _uiState.value = CityDetailUiState.Error
                }
                .mapNotNull { it }
                .collect { it ->
                    Log.d("CityDetailViewModel", "Fetching $it")
                    _uiState.value = CityDetailUiState.Loading
                    combine(
                        meteoInfoRepository.getForecastForCity(it.lat, it.lon)
                            .map { Result.success(it) }
                            .catch { emit(Result.failure(it)) },
                        meteoInfoRepository.getCurrentWeatherForCity(it.lat, it.lon)
                            .map { Result.success(it) }
                            .catch { emit(Result.failure(it)) },
                    ) { forecast, currentWeather ->

                        if (forecast.isFailure && currentWeather.isFailure) {
                            //biga problema, network down?
                            _uiState.value = CityDetailUiState.Error
                        } else {
                            _uiState.value = CityDetailUiState.Success(
                                city = it,
                                forecast = forecast.getOrNull(),
                                current = currentWeather.getOrNull(),
                            )

                            //Attempt to add collectible
                            currentWeather.getOrNull()?.weatherCode?.let { weatherCode ->
                                addCollectible(weatherCode.code)
                            }

                        }
                    }.catch { _uiState.value = CityDetailUiState.Error }.collect {}
                }
        }
    }

    fun deleteCity(id: Int) {
        citiesRepository.deleteById(id)
    }

    suspend fun addCollectible(code: Int) {
        collectiblesRepository.insertIfNotExist(
            Collectible(
                code = code,
                dateCollected = System.currentTimeMillis()
            )
        )
    }

}


