package com.example.weathercheck2000.ui.addCity

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weathercheck2000.data.repository.CitiesRepository
import com.example.weathercheck2000.database.cities.City

class AddCityViewModel(
    private val repository: CitiesRepository
) : ViewModel() {

    fun addCity(
        userInputCity: String,
        userInputLatitude: String,
        userInputLongitude: String,
    ) : Boolean {

        val name = userInputCity
        val latitude = userInputLatitude
        val longitude = userInputLongitude
        //TODO some validation
        val newCity = City(name = name, lat = latitude, lon = longitude)

        try {
            repository.insert(newCity)
            return true
        } catch (e: Exception) {
            Log.e("ADD_CITY error ", e.toString())
            return false
        }

    }

}