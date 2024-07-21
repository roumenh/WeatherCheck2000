package com.example.weathercheck2000.ui.about

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.weathercheck2000.R

@Composable
fun AboutScreen(
    onBackPressed: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row {
            Image(
                modifier = Modifier
                    .clickable { onBackPressed() },
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = stringResource(R.string.back)
            )
            Text(
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(25.dp))
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                    )
                    .padding(horizontal = 15.dp),
                text = stringResource(R.string.about),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Text(
            text = "Aplikace Psí Počasí v. 1.0"
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.about_text)
        )

    }

}