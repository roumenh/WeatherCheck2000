package com.example.weathercheck2000.ui.addCity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weathercheck2000.R

@Composable
fun AddCityScreen(
    onAddCity: (String, String, String) -> Unit
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

            var name by remember { mutableStateOf("") }
            var lat by remember { mutableStateOf("") }
            var lon by remember { mutableStateOf("") }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(id = R.string.add_city_name)) }
            )

            OutlinedTextField(
                value = lat,
                onValueChange = { lat = it },
                label = { Text(stringResource(id = R.string.add_city_latitude)) }
            )

            OutlinedTextField(
                value = lon,
                onValueChange = { lon = it },
                label = { Text(stringResource(id = R.string.add_city_longitude)) }
            )

            Button(onClick = { onAddCity(name, lat, lon) }) {
                Text("Add city")
            }

            HorizontalDivider()

            Button(onClick = { onAddCity("Loos", "61.7365133", "15.1692092") }) {
                Text ("Add Loos")
            }
            Button(onClick = { onAddCity("Brno", "49.2002211", "16.6078411") }) {
                Text ("Add Brno")
            }
            Button(onClick = { onAddCity("Alicante", "38.3436364", "0.4881708") }) {
                Text ("Add Alicante")
            }

        }
    }

}