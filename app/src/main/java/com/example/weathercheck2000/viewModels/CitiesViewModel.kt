package com.example.weathercheck2000.viewModels

import CitiesRepository
import android.util.Log
import androidx.lifecycle.*
import com.example.weathercheck2000.database.cities.Cities
import com.example.weathercheck2000.database.cities.CitiesDao
import com.example.weathercheck2000.network.WeatherApi
import java.lang.IllegalArgumentException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//class CitiesViewModel (private val citiesDao: CitiesDao): ViewModel(){   // DAO
class CitiesViewModel (private val repository: CitiesRepository): ViewModel(){      // repository

    val userInputCity = MutableLiveData<String>()

    private var _inputCity = MutableLiveData<String>()
    val inputCity: LiveData<String>
        get() = _inputCity

    // test text for testing what comes back from API requests
    /*
    var _testText = MutableLiveData<String>()
    val testText : LiveData<String>
        get() = _testText
*/
    val testText = MutableLiveData<String>()
    // fun getAllCities(): Flow<List<Cities>> = citiesDao.getAll() // DAO
    val allCities: LiveData<MutableList<Cities>> = repository.allCities.asLiveData()  // Repository

    /*
    fun insertNewCity() = viewModelScope.launch{
        citiesDao.insertCity(Cities(13,"Brno","111","222"))
    }
    */

    private fun getWeatherForecast(){
        viewModelScope.launch {
            val result = WeatherApi.retrofitService.getForecast("38.7072","-9.1355")
            testText.value = result
            //testText.value = "DASOIDJSAOJD"
            Log.d("Retrofit",testText.value.toString())
        }
    }

    init {
        userInputCity.value = "Praha"
        _inputCity.value = "Praha"
        testText.value = "test text"
        getWeatherForecast()
    }

    fun insertNewCity() {
        viewModelScope.launch {
            Log.d("Cities","test")
            Log.d("CitiesViewModel",userInputCity.value!!)
            var name = userInputCity.value!!
            var latitude = "000"
            var longitude = "123"
            val newCity = Cities(name = name, lat = latitude, lon = longitude)
            repository.insert(newCity)
            //  citiesDao.insertCity(name!!,latitude,longitude)
        }


    }



    // live data:

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    class CitiesViewModelFactory(
        //private val citiesDao: CitiesDao
        private val citiesRepository: CitiesRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create (modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CitiesViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                //return CitiesViewModel(citiesDao) as T
                return CitiesViewModel(citiesRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}