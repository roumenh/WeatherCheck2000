package com.example.weathercheck2000.ui.home

import android.graphics.Color.alpha
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercheck2000.R
import com.example.weathercheck2000.database.cities.City
import com.example.weathercheck2000.ui.theme.RobinTheme

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onCityClicked: (Int) -> Unit,
    onAddCityClicked: () -> Unit,
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .fillMaxWidth()
    ) {



        Text(
            modifier = Modifier
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(25.dp))
                .background(
                    color = MaterialTheme.colorScheme.primary, //.copy(alpha = 0.39f)
                    //shape = RoundedCornerShape(25.dp)
                )

                .padding(horizontal = 25.dp),
            text = stringResource(R.string.title_home),
            style = MaterialTheme.typography.headlineLarge
        )

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
                    onClick = { onCityClicked(cityTemp.first.id) }
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Text(
                            text = cityTemp.first.name,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = cityTemp.second.toString() + " °C"
                        )
                    }
                }
            }


        }

        HorizontalDivider()

        Button(onClick = onAddCityClicked) {
            Text("Add city")
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
                    City(0, "Oslo", "1", "2") to 10.0,
                    City(0, "Loos", "1", "2") to 10.0,
                    City(0, "Náměšť nad Oslavou", "1", "2") to 10.0,
                    City(0, "Oslo", "1", "2") to 10.0,
                )
            ),
            onCityClicked = {},
            onAddCityClicked = {},
        )
    }

}