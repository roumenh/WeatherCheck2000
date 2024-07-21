package com.example.weathercheck2000.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercheck2000.data.repository.CollectiblesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class GalleryUiState {

    data object Loading : GalleryUiState()

    data object Error : GalleryUiState()

    data class Success(
        val listOfCollectedCodes: List<Int>,
    ) : GalleryUiState()
}


class GalleryViewModel(
    private val collectiblesRepository: CollectiblesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<GalleryUiState>(GalleryUiState.Loading)
    var uiState = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            collectiblesRepository.allCollectibles.collect{

                _uiState.value = GalleryUiState.Success(
                    listOfCollectedCodes = it.map { it.code }.toMutableList()
                )

            }
        }
    }

}


