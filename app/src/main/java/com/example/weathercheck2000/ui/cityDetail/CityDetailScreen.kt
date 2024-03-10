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
        uiState.meteoInfo?.let {
            Text("today min: " + it.todayMinTemperature.toString() + " celsius")
            Text("today max: " + it.todayMaxTemperature.toString() + " celsius")
        }
    }


}
