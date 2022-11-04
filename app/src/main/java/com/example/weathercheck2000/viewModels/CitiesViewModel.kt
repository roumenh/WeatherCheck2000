package com.example.weathercheck2000.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathercheck2000.database.cities.Cities
import com.example.weathercheck2000.database.cities.CitiesDao
import java.lang.IllegalArgumentException
import kotlinx.coroutines.flow.Flow

class CitiesViewModel (private val citiesDao: CitiesDao): ViewModel(){

    fun getAllCities(): Flow<List<Cities>> = citiesDao.getAll()

    fun insertNewCity(name: String, latitude: String, longitude: String) = citiesDao.insertCity(name,latitude,longitude)
}

class CitiesViewModelFactory(
    private val citiesDao: CitiesDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create (modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitiesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CitiesViewModel(citiesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}