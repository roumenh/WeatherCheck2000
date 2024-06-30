package com.example.weathercheck2000.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercheck2000.R
import com.example.weathercheck2000.database.cities.City

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onCityClicked: (Int) -> Unit,
    onAddCityClicked: () -> Unit,
) {

    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Text (
            text = stringResource(R.string.title_home),
            style = MaterialTheme.typography.headlineLarge
        )

        uiState.citiesAndCurrentTemperatures?.forEach {
            Button(
                onClick = { onCityClicked(it.first.id) }
            ){
                Text (it.first.name + " - " + it.second.toString() + " °C")
            }

        }

        HorizontalDivider()

        Button(onClick = onAddCityClicked ) {
            Text("Add city")
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            uiState = HomeUiState(
                mutableListOf(
                    City(0, "Oslo", "1", "2") to 10.0,
                )
            ),
            onCityClicked = {},
            onAddCityClicked = {},
        )
    }

}