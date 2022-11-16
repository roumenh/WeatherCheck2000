package com.example.weathercheck2000.viewModels

import CitiesRepository
import android.util.Log
import androidx.lifecycle.*
import com.example.weathercheck2000.database.cities.Cities
import com.example.weathercheck2000.network.CurrentWeatherConditions
import com.example.weathercheck2000.network.WeatherApi
import java.lang.IllegalArgumentException
import kotlinx.coroutines.launch

//class CitiesViewModel (private val citiesDao: CitiesDao): ViewModel(){   // DAO
class CitiesViewModel (private val repository: CitiesRepository): ViewModel(){      // repository

    val userInputCity = MutableLiveData<String>()
    val userInputLatitude = MutableLiveData<String>()
    val userInputLongitude = MutableLiveData<String>()

    private val _city = MutableLiveData<Cities>()
    var city : LiveData<Cities> = _city

    // to store current weather for the selected city.
    private val _currentWeatherConditions = MutableLiveData<CurrentWeatherConditions>()
    var currentWeatherConditions : LiveData<CurrentWeatherConditions> = _currentWeatherConditions

    private val _testText = MutableLiveData<String>()
    var testText: LiveData<String> = _testText
    // fun getAllCities(): Flow<List<Cities>> = citiesDao.getAll() // DAO
    val allCities: LiveData<MutableList<Cities>> = repository.allCities.asLiveData()  // Repository


    private fun requestWeatherForecast(latitude : String, longitude : String){
        viewModelScope.launch {
            try {
                val result = WeatherApi.retrofitService.getForecast(latitude, longitude)
                Log.d("Retrofit",testText.value.toString())
                _testText.value = result.daily.temperature2mMin.first().toString()
            }catch (e: Exception){
                _testText.value = "Error : ${e.message}"
            }
            //testText.value = "DASOIDJSAOJD"

        }
    }

    init {
        _testText.value = "test text"
        Log.d("viewModel","Init")
    }

    fun insertNewCity() : Boolean {

        // TODO data validation (latitude, longitude)
        // TODO - idea - pick coordinates from map or from current position GPS

        // WHY - it is interesting that after submitting , the fields will be cleared if I want to
        // submit another city. If I dont submit, it will keep double-binded to the variables
        // but if submit - it will clear. Why?

            Log.d("Cities","test")

            if (userInputCity.value != null && userInputLatitude.value != null && userInputLongitude.value != null){
                val name        = userInputCity.value!!
                val latitude    = userInputLatitude.value!!
                val longitude   = userInputLongitude.value!!
                val newCity = Cities(name = name, lat = latitude, lon = longitude)
                viewModelScope.launch {
                    repository.insert(newCity)
                }
                return true
            }else{
                return false
            }

    }

    fun deleteSelectedCity(city: Cities) {
        viewModelScope.launch {
            repository.delete(city)
        }
        // TODO error: Index out of bounds when deleting the city. all works but the
        // adapter is then behaving uncorrectly.
    }

    fun setDetailCity(newCity: Cities){
        _city.value = newCity
        viewModelScope.launch {
            try {
                _currentWeatherConditions.value =
                    WeatherApi.retrofitService.requestCurrentWeather(city.value!!.lat,city.value!!.lon)
            }catch (e: Exception){
                Log.d("Error",e.message.toString())
            }
        }
    }



    class CitiesViewModelFactory(
        //private val citiesDao: CitiesDao
        private val citiesRepository: CitiesRepository
    ): ViewModelProvider.Factory  {
        override fun <T : ViewModel> create (modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CitiesViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return CitiesViewModel(citiesRepository) as T
                //return CitiesViewModel(citiesDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}

