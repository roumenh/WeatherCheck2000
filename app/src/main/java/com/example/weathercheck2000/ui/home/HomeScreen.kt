package com.example.weathercheck2000.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onCityClicked: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()

    Column {

        Text ("home screen")

        Button(
            onClick = { onCityClicked("Stockholm") }
        ){
            Text ("Stockholm")
        }

        uiState.value.cities?.forEach {
            Button(
                onClick = { onCityClicked(it.name) }
            ){
                Text (it.name)
            }

        }
    }



}