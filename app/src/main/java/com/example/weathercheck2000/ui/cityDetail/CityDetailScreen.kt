package com.example.weathercheck2000.ui.cityDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercheck2000.R
import com.example.weathercheck2000.data.model.CurrentWeather
import com.example.weathercheck2000.data.model.WeatherCode
import com.example.weathercheck2000.data.model.WeatherForecast
import com.example.weathercheck2000.database.cities.City
import com.example.weathercheck2000.ui.theme.RobinTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityDetailScreen(
    uiState: CityDetailUiState,
    initialCityId: Int,
    listOfAllCities: List<City>,
    fetchDataForCityId: (Int) -> Unit,
) {

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        containerColor = Color.Transparent,
    ) { padding ->

        val selectedCityId by remember { mutableIntStateOf(initialCityId-1) }

        if (listOfAllCities.isNotEmpty()) {
            LaunchedEffect(selectedCityId) {
                fetchDataForCityId(listOfAllCities[selectedCityId].id)
            }
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (uiState) {
                is CityDetailUiState.Loading -> {
                    Text("Loading")
                }

                is CityDetailUiState.Error -> {
                    Text(
                        modifier = Modifier.padding(20.dp).align(Alignment.CenterHorizontally),
                        text = "Something \uD83D\uDCA9 is wrong",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }

                else -> {

                    val successState = uiState as CityDetailUiState.Success

                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            modifier = Modifier
                                .clickable { },
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = "Previous"
                        )

                        Text(
                            modifier = Modifier
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(25.dp))
                                .weight(1f)
                                .background(color = MaterialTheme.colorScheme.primary)
                                .padding(horizontal = 25.dp),
                            text = successState.cityName,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )

                        Image(
                            modifier = Modifier.clickable { },
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = "Next"
                        )
                    }

                    //Current weather
                    successState.current?.let {

                        Box {
                            val colorStops = arrayOf(
                                0.0f to Color.Transparent,
                                0.3f to MaterialTheme.colorScheme.background,
                                0.7f to MaterialTheme.colorScheme.background,
                                1.0f to Color.Transparent,
                            )

                            //Robin image
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Brush.horizontalGradient(colorStops = colorStops))
                                    .graphicsLayer { alpha = 0.99f }
                                    .drawWithContent {
                                        drawContent()
                                        drawRect(
                                            brush = Brush.verticalGradient(colorStops = colorStops),
                                            blendMode = BlendMode.DstIn
                                        )
                                    },
                                contentScale = ContentScale.Crop,
                                painter = painterResource(robinImageResolver(currentWeather = it)),
                                contentDescription = "TODO"
                            )

                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = stringResource(
                                        R.string.celsius,
                                        it.temperature.toString()
                                    ),
                                    style = MaterialTheme.typography.headlineLarge
                                )

                                Image(
                                    modifier = Modifier
                                        .size(80.dp),
                                    painter = painterResource(id = it.weatherCode!!.imageId),
                                    contentDescription = it.weatherCode.description,
                                )
                            }


                        }

                        Spacer(Modifier.height(16.dp))

                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.air_24px),
                                contentDescription = stringResource(id = R.string.current_temperature)
                            )
                            Text(
                                stringResource(
                                    R.string.kilometers_per_hour,
                                    it.windSpeed.toString()
                                )
                            )
                        }

                        Image(
                            modifier = Modifier
                                .size(64.dp)
                                .rotate(it.windDirection.toFloat()),
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = stringResource(
                                R.string.kilometers_per_hour,
                                it.windSpeed.toString()
                            ),
                        )

                    }


                    //Todays forecast
                    successState.forecast?.let {

                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = stringResource(R.string.todays_forecast),
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(stringResource(R.string.minimum_temperature))
                            Spacer(Modifier.weight(1f))
                            Text(
                                stringResource(
                                    R.string.celsius,
                                    it.todayMinTemperature.toString()
                                )
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(stringResource(R.string.maximum_temperature))
                            Spacer(Modifier.weight(1f))
                            Text(
                                stringResource(
                                    R.string.celsius,
                                    it.todayMaxTemperature.toString()
                                )
                            )
                        }

                    }


                }
            }

        }
    }
}


@Preview
@Composable
fun CityDetailScreenPreview() {
    RobinTheme {
        CityDetailScreen(
            uiState =
            CityDetailUiState.Success(
                cityName = "Náměšť nad Oslavou",
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
            listOfAllCities = listOf(
                City(
                    id = 0,
                    name = "Frňákov",
                    "lat", "lon"
                )
            ),
            initialCityId = 2,
            fetchDataForCityId = {},
        )
    }
}
