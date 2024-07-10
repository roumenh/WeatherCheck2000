package com.example.weathercheck2000.ui.addCity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercheck2000.R
import com.example.weathercheck2000.data.utils.getCurrentDateInCustomFormat
import com.example.weathercheck2000.ui.theme.RobinTheme

@Composable
fun AddCityScreen(
    onAddCity: (String, String, String) -> Unit
) {

//TODO back button

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

            Text(
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(25.dp))
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                    )
                    .padding(horizontal = 15.dp),
                text = stringResource(R.string.add_city),
                style = MaterialTheme.typography.headlineMedium
            )

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

@Preview
@Composable
fun AddCityScreenPreview() {
    RobinTheme {
        AddCityScreen(
            onAddCity = { _, _, _ -> }
        )
    }
}
