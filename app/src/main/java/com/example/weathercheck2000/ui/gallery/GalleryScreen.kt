package com.example.weathercheck2000.ui.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.weathercheck2000.R
import com.example.weathercheck2000.data.model.mapOfWeatherCodes
import com.example.weathercheck2000.ui.components.ErrorMessage
import com.example.weathercheck2000.ui.components.ZoomableImage
import com.example.weathercheck2000.ui.theme.RobinTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GalleryScreen(
    uiState: GalleryUiState,
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
                text = stringResource(R.string.gallery),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(Modifier.height(16.dp))

        when (uiState) {

            GalleryUiState.Error -> {
                ErrorMessage()
            }

            GalleryUiState.Loading -> {
                Text(stringResource(R.string.loading))
            }

            is GalleryUiState.Success -> {

                var imageDialog: Int? by remember { mutableStateOf(null) }

                if (imageDialog != null) {
                    Dialog(
                        properties = DialogProperties(usePlatformDefaultWidth = false),
                        onDismissRequest = { imageDialog = null }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            ZoomableImage(imageDialog!!)
                        }
                        Image(
                            modifier = Modifier
                                .clickable { imageDialog = null },
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {

                    mapOfWeatherCodes.forEach {
                        it.value.robinImage?.let { image ->
                            item(it.key) {

                                Box(
                                    modifier = Modifier
                                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(25.dp))
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                        .padding(12.dp)
                                ) {
                                    if (uiState.listOfCollectedCodes.contains(it.value.code)) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Image(
                                                modifier = Modifier
                                                    .heightIn(max = 140.dp)
                                                    .shadow(
                                                        elevation = 1.dp,
                                                        shape = RoundedCornerShape(12.dp)
                                                    )
                                                    .clickable { imageDialog = image },
                                                contentScale = ContentScale.FillWidth,
                                                painter = painterResource(image),
                                                contentDescription = stringResource(it.value.description)
                                            )
                                            Text(
                                                textAlign = TextAlign.Center,
                                                text = stringResource(it.value.description)
                                            )
                                            Image(
                                                painter = painterResource(it.value.imageId),
                                                contentDescription = stringResource(it.value.description)
                                            )
                                        }

                                    } else {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Image(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.5f)
                                                    .shadow(
                                                        elevation = 1.dp,
                                                        shape = RoundedCornerShape(12.dp)
                                                    ),
                                                contentScale = ContentScale.FillWidth,
                                                painter = painterResource(R.drawable.ic_locked),
                                                contentDescription = "Locked"
                                            )
                                            Text(
                                                textAlign = TextAlign.Center,
                                                text = stringResource(it.value.description)
                                            )
                                            Image(
                                                modifier = Modifier.size(50.dp),
                                                painter = painterResource(it.value.imageId),
                                                contentDescription = stringResource(it.value.description)
                                            )

                                        }
                                    }

                                }

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
fun GalleryScreenPreview() {
    RobinTheme {
        GalleryScreen(
            uiState = GalleryUiState.Success(
                listOfCollectedCodes = listOf(1)
            ),
            onBackPressed = {}
        )
    }
}