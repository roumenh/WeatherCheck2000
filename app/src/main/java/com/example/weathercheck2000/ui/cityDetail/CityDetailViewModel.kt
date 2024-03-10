package com.example.weathercheck2000.ui.cityDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.model.MeteoInfo
import com.example.weathercheck2000.data.repository.MeteoInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CityDetailUiState(
    val meteoInfo: MeteoInfo?,
)

class CityDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CityDetailUiState(null))
    val uiState = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch {
            //TODO introduce DI for repository
            val meteoInfo = MeteoInfoRepository().getMeteoInfoForCity("25.5","25.0") //TODO , connect to actual city from the first screen
            _uiState.value = CityDetailUiState(meteoInfo)
            //TODO improve states...
        }
    }

}