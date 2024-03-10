package com.example.weathercheck2000.ui.cityDetail;

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CityDetailScreen(
    uiState: CityDetailUiState
) {
    Column {
        Text ("aha")
        uiState.forecast?.let {
            Text("today min: " + it.todayMinTemperature.toString() + " celsius")
            Text("today max: " + it.todayMaxTemperature.toString() + " celsius")
        }
        uiState.current?.let {
            Text("current: " + it.temperature.toString() + " celsius")
            Text("wind: " + it.windDirection.toString() + " direction")
            Text("wind: " + it.windSpeed.toString() + " speed")
            Text("weather code: " + it.weatherCode.toString() + " ou ou")
        }
    }


}
