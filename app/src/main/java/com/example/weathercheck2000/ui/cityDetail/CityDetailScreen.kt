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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
    initialCityId: Int?,
    listOfAllCities: List<City>,
    fetchDataForCityId: (Int) -> Unit,
) {

    Scaffold(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        containerColor = Color.Transparent,
    ) { padding ->

        val pagerState = rememberPagerState(pageCount = {
            listOfAllCities.size
        })

        val coroutineScope = rememberCoroutineScope()

        HorizontalPager(
            userScrollEnabled = false, //todo implement better loading and then nicer scroll
            state = pagerState
        ) { page ->

            LaunchedEffect(page) {
                fetchDataForCityId(listOfAllCities[page].id)
            }

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (initialCityId == null) {
                    Text("Loading or error")
                } else {

                    if (uiState is CityDetailUiState.Loading) {
                        Text("Loading")
                    } else if (uiState is CityDetailUiState.Error) {
                        Text("Error")
                    } else {

                        val successState = uiState as CityDetailUiState.Success

                        Row(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                modifier = Modifier.clickable {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                },
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
                                style = MaterialTheme.typography.headlineLarge,
                                textAlign = TextAlign.Center
                            )

                            Image(
                                modifier = Modifier.clickable  {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                },
                                painter = painterResource(R.drawable.ic_arrow_right),
                                contentDescription = "Next"
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        //Current weather
                        successState.current?.let {

                            Box {
                                Image(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop,
                                    painter = painterResource(R.drawable.img_placeholder),
                                    contentDescription = "TODO"
                                )

                                Column(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(16.dp)
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.thermostat_24px),
                                        contentDescription = stringResource(id = R.string.current_temperature)
                                    )
                                    Text(
                                        stringResource(
                                            R.string.celsius,
                                            it.temperature.toString()
                                        )
                                    )



                                    Image(
                                        modifier = Modifier
                                            .size(64.dp),
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
