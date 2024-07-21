package com.example.weathercheck2000.ui.cityDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import com.example.weathercheck2000.ui.components.ErrorMessage
import com.example.weathercheck2000.ui.theme.RobinTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CityDetailScreen(
    uiState: CityDetailUiState,
    initialCityId: Int,
    listOfAllCities: List<City>,
    fetchDataForCityId: (Int) -> Unit,
    onDeleteCityClicked: (Int) -> Unit,
) {

    val dateFormatter = DateTimeFormatter.ofPattern("d. M.")

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        containerColor = Color.Transparent,
    ) { padding ->

        val selectedCityId by remember { mutableIntStateOf(initialCityId - 1) }

        if (listOfAllCities.isNotEmpty()) {
            LaunchedEffect(selectedCityId) {
                fetchDataForCityId(initialCityId)
            }
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            when (uiState) {
                is CityDetailUiState.Loading -> {
                    Text(stringResource(R.string.loading))
                }

                is CityDetailUiState.Error -> {
                    ErrorMessage()
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
                            modifier = Modifier.clickable { },
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = "Previous"
                        )

                        Text(
                            modifier = Modifier
                                .shadow(
                                    elevation = 4.dp, shape = RoundedCornerShape(25.dp)
                                )
                                .weight(1f)
                                .background(color = MaterialTheme.colorScheme.primary)
                                .padding(horizontal = 25.dp),
                            text = successState.city.name,
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
                            Image(modifier = Modifier
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
                                contentScale = ContentScale.FillWidth,
                                //  painter = painterResource(R.drawable.rimg_0_clear_sky),
                                painter = painterResource(robinImageResolver(currentWeather = it)),
                                contentDescription = "TODO"
                            )

                            LaunchedEffect(true) {
                                val weatherCode = it.weatherCode

                            }

                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = stringResource(
                                        R.string.celsius, it.temperature.toString()
                                    ), style = MaterialTheme.typography.headlineLarge
                                )

                                Image(
                                    modifier = Modifier.size(80.dp),
                                    painter = painterResource(id = it.weatherCode!!.imageId),
                                    contentDescription = it.weatherCode.description,
                                )


                            }


                        }

                        Spacer(Modifier.height(16.dp))

                    }

                    Column(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp)
                    ) {

                        //forecast
                        successState.forecast?.let {
                            Text(
                                text = stringResource(R.string.upcoming_days_forecast),
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Row {
                                it.futureMinTemperatures.forEachIndexed { index, temperature ->

                                    Column(
                                        modifier = Modifier.padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        //Date today and next 7 days based by index
                                        Text(
                                            LocalDate.now().plusDays(index.toLong())
                                                .format(dateFormatter)
                                        )
                                        Image(
                                            modifier = Modifier.size(40.dp),
                                            painter = painterResource(id = it.weatherCodes[index].imageId),
                                            contentDescription = it.weatherCodes[index].description,
                                        )
                                        Text(
                                            stringResource(
                                                R.string.min_and_max_temp,
                                                it.futureMaxTemperatures[index].toInt(),
                                                temperature.toInt()
                                            )
                                        )
                                    }


                                }
                            }

                        }

                    }
                }
            }
            Spacer(Modifier.height(32.dp))

            Button(onClick = { onDeleteCityClicked(selectedCityId) }) {
                Text("Delete city")
            }

        }
    }
}


@Preview(heightDp = 2000)
@Composable
fun CityDetailScreenPreview() {
    RobinTheme {
        CityDetailScreen(
            uiState = CityDetailUiState.Success(
                city = City(1, "Náměšť nad Oslavou", "0", "0"),
                forecast = WeatherForecast(
                    todayMinTemperature = 5.0,
                    todayMaxTemperature = 35.2,
                    futureMinTemperatures = listOf(2.0, 3.0, 4.0),
                    futureMaxTemperatures = listOf(5.0, 6.0, 7.0),
                    weatherCodes = listOf(
                        WeatherCode(
                            imageId = R.drawable.code2,
                            description = "",
                            robinImage = R.drawable.rimg_45_fog,
                            code = 45
                        ),
                        WeatherCode(
                            imageId = R.drawable.code71,
                            description = "",
                            robinImage = R.drawable.rimg_45_fog,
                            code = 45
                        ),
                        WeatherCode(
                            imageId = R.drawable.code53,
                            description = "",
                            robinImage = R.drawable.rimg_45_fog,
                            code = 45
                        ),
                    )
                ),
                current = CurrentWeather(
                    temperature = 15.7,
                    windSpeed = 178.0,
                    windDirection = 65.7,
                    weatherCode = WeatherCode(
                        R.drawable.code2, "Clear sky", null, 0
                    ),
                )
            ),
            listOfAllCities = listOf(
                City(
                    id = 0, name = "Frňákov", "lat", "lon"
                )
            ),
            initialCityId = 2,
            onDeleteCityClicked = {},
            fetchDataForCityId = {},
        )
    }
}
