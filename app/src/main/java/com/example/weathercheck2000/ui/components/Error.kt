package com.example.weathercheck2000.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weathercheck2000.R

@Composable
fun ErrorMessage(){
    Text(
        modifier = Modifier
            .padding(20.dp),
        text = stringResource(R.string.something_is_wrong),
        style = MaterialTheme.typography.headlineLarge,
    )
}