package com.example.weathercheck2000.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercheck2000.R
import com.example.weathercheck2000.data.utils.getCurrentDateInCustomFormat
import com.example.weathercheck2000.database.cities.City
import com.example.weathercheck2000.ui.theme.RobinTheme

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onCityClicked: (Int) -> Unit,
    onOpenGalleryClicked: () -> Unit,
    onAboutClicked: () -> Unit,
    onAddCityClicked: () -> Unit,
) {

    Scaffold (
        floatingActionButton = {
            Button(onClick = onAboutClicked) {
                Text (stringResource(R.string.about))
            }
        }
    ) { padding ->
        
    Column(
        modifier = Modifier
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 22.dp)
            .fillMaxWidth()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {


        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onAddCityClicked() },
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = stringResource(R.string.add)
            )

            Text(
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(25.dp))
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                    )
                    .padding(horizontal = 15.dp),
                text = getCurrentDateInCustomFormat(),
                style = MaterialTheme.typography.headlineMedium
            )

            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clickable { onOpenGalleryClicked() },
                painter = painterResource(id = R.drawable.ic_gallery),
                contentDescription = stringResource(R.string.gallery)
            )


        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            items(uiState.citiesAndCurrentTemperatures.size) { index ->

                val cityTemp = uiState.citiesAndCurrentTemperatures[index]
                Card(
                    modifier = Modifier,
                    colors = CardDefaults.cardColors().copy(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(48.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = { onCityClicked(cityTemp.city.id) }
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = cityTemp.city.name,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.W500,
                        )
                        if (cityTemp.currentTemperature == null) {
                            //Poo.. something is wrong!💩
                            Text(
                                text = "\uD83D\uDCA9",
                                style = MaterialTheme.typography.headlineLarge,
                            )
                        } else {
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = cityTemp.currentTemperature?.toInt().toString() + " °C",
                                fontWeight = FontWeight.W500,
                            )
                            val minAndMax = buildAnnotatedString {
                                val lowTemp = cityTemp.forecastTemperatureLow?.toInt().toString()
                                val highTemp = cityTemp.forecastTemperatureHigh?.toInt().toString()
                                withStyle(style = SpanStyle(color = Color(0xFF0000DD))) {
                                    append("$lowTemp°C")
                                }
                                append("/")
                                withStyle(style = SpanStyle(color = Color(0xFFB0581D))) {
                                    append("$highTemp°C")
                                }
                            }
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = minAndMax,
                                fontWeight = FontWeight.W500,
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
fun HomeScreenPreview() {
    RobinTheme {
        HomeScreen(
            uiState = HomeUiState(
                mutableListOf(
                    HomeSingleCityState(
                        city = City(0, "Oslo", "1", "2"),
                        currentTemperature = 10.0,
                        forecastTemperatureLow = 10.0,
                        forecastTemperatureHigh = 10.0
                    ),
                    HomeSingleCityState(
                        city = City(0, "Náměšť nad oslavou", "1", "2"),
                        currentTemperature = 10.0,
                        forecastTemperatureLow = 10.0,
                        forecastTemperatureHigh = 10.0
                    ),
                )
            ),
            onCityClicked = {},
            onOpenGalleryClicked = {},
            onAboutClicked = {},
            onAddCityClicked = {},
        )
    }

}