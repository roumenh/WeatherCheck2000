package com.example.weathercheck2000.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.repository.CitiesRepository
import com.example.weathercheck2000.database.cities.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class HomeUiState(
    val cities: List<City>?,
)

class HomeViewModel (
    private val repository: CitiesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(null))
    val uiState = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {

        viewModelScope.launch {
            repository.allCities.collect {
                _uiState.value = HomeUiState(cities = it)
            }
        }

    }

}