package com.example.weathercheck2000.data.repository

import androidx.annotation.WorkerThread
import com.example.weathercheck2000.database.cities.CitiesDao
import com.example.weathercheck2000.database.cities.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Repository for the database - source of cities
 */
interface CitiesRepository {

    val allCities:Flow<MutableList<City>>
    fun insert(city: City)
    fun delete(city: City)
}

class CitiesRepositoryImpl(private val citiesDao: CitiesDao) : CitiesRepository {

    override val allCities:Flow<MutableList<City>> = citiesDao.getAll()

    @WorkerThread
    override fun insert(city: City){
        CoroutineScope(Dispatchers.IO).launch {
            citiesDao.insertCity(city)
        }
    }

    @WorkerThread
    override fun delete(city: City){
        CoroutineScope(Dispatchers.IO).launch {
            citiesDao.deleteCity(city)
        }
    }

}