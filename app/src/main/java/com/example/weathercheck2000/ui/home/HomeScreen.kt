package com.example.weathercheck2000.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onCityClicked: (String) -> Unit,
) {
    Column {

        Text ("home screen")

        Button(
            onClick = { onCityClicked("Stockholm") }
        ){
            Text ("Stockholm")
        }
    }



}