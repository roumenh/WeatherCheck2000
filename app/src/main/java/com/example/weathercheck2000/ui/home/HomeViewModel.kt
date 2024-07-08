package com.example.weathercheck2000.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.repository.CitiesRepository
import com.example.weathercheck2000.data.repository.MeteoInfoRepository
import com.example.weathercheck2000.database.cities.City
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch


data class HomeUiState(
    val citiesAndCurrentTemperatures: List<Pair<City, Double?>>,
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

        val listOfCitiesWithTemperatures: MutableList<Pair<City, Double?>> = mutableListOf()

        viewModelScope.launch {
            repository.allCities.collect { result ->

                result.forEach { city ->
                    meteoInfoRepository.getCurrentWeatherForCity(city.lat, city.lon)
                        .catch {
                            listOfCitiesWithTemperatures.add(Pair(city, null))
                            Log.e("HomeViewModel", "Error fetching data", it)
                        }
                        .collectLatest {
                            listOfCitiesWithTemperatures.add(Pair(city, it.temperature))
                            Log.d("HomeViewModel", "Adding city to list ${city.name}")
                        }

                }

                _uiState.value =
                    HomeUiState(citiesAndCurrentTemperatures = listOfCitiesWithTemperatures)


                //TODO introduce some loading state!

            }
        }

    }

}