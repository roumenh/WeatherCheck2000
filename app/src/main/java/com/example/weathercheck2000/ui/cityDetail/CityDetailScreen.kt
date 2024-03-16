package com.example.weathercheck2000.ui.cityDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercheck2000.R
import com.example.weathercheck2000.WeatherCode
import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherForecast

@Composable
fun CityDetailScreen(
    uiState: CityDetailUiState,
    cityName: String,
) {


    Scaffold(
        modifier = Modifier.padding(16.dp),
        containerColor = Color.Transparent,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text (
                text = cityName, //TODO replace with the actual city
                style = MaterialTheme.typography.headlineLarge,
            )

            HorizontalDivider(Modifier.padding(vertical = 8.dp))

            //Todays forecast
            uiState.forecast?.let {

                Text (
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.todays_forecast),
                    style = MaterialTheme.typography.headlineSmall
                )

                    Row(
                    modifier = Modifier.fillMaxWidth(),
                ){
                    Text(stringResource(R.string.minimum_temperature))
                    Spacer(Modifier.weight(1f))
                    Text(stringResource(R.string.celsius, it.todayMinTemperature.toString()))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ){
                    Text(stringResource(R.string.maximum_temperature))
                    Spacer(Modifier.weight(1f))
                    Text(stringResource(R.string.celsius, it.todayMaxTemperature.toString()))
                }

            }

            //Current weather
            uiState.current?.let {

                Text (
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.current_weather),
                    style = MaterialTheme.typography.headlineSmall
                )

                Row(){
                    Icon(
                        painter = painterResource(R.drawable.thermostat_24px),
                        contentDescription = stringResource(id = R.string.current_temperature)
                    )
                    Text(stringResource(R.string.celsius, it.temperature.toString()))
                }


                Image(
                    modifier = Modifier
                        .size(64.dp),
                    painter = painterResource(id = it.weatherCode!!.imageId),
                    contentDescription = it.weatherCode.description,
                )

                Spacer(Modifier.height(16.dp))

                Row(){
                    Icon(
                        painter = painterResource(R.drawable.air_24px),
                        contentDescription = stringResource(id = R.string.current_temperature)
                    )
                    Text(stringResource(R.string.kilometers_per_hour, it.windSpeed.toString()))
                }

                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .rotate(it.windDirection.toFloat()),
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = stringResource(R.string.kilometers_per_hour, it.windSpeed.toString()),
                )

            }
        }

    }


}


@Preview
@Composable
fun CityDetailScreenPreview(){
    MaterialTheme {
        CityDetailScreen(
            uiState =
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
                        R.drawable.code2,
                        "Clear sky"
                    ),
                )
            ),
            cityName = "Paris"
        )
    }
}
