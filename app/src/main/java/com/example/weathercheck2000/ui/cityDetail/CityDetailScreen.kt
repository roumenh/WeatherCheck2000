package com.example.weathercheck2000.ui.cityDetail;

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.weathercheck2000.R
import com.example.weathercheck2000.WeatherCode
import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherForecast

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


@Preview
@Composable
fun CityDetailScreenPreview(){
    MaterialTheme {
        CityDetailScreen(uiState =
            CityDetailUiState(
                forecast = WeatherForecast(
                    todayMinTemperature = 5.0,
                    todayMaxTemperature = 35.2
                ),
                current = CurrentWeather(
                    temperature = 15.7,
                    windSpeed = 178.0,
                    windDirection = 65.7,
                    weatherCode = WeatherCode(
                        R.drawable.code0,
                        "Clear sky"
                    ),
                )
            )
        )
    }
}
