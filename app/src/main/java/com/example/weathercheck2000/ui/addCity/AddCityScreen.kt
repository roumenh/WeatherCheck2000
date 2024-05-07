package com.example.weathercheck2000.ui.addCity

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AddCityScreen (
    onAddCity: (String, String, String) -> Unit
){

    Button(onClick = { onAddCity("Brno", "49.195278", "16.608333") }) {
        Text ("přidej brno")
    }

}