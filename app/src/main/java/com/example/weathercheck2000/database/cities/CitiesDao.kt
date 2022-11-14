package com.example.weathercheck2000.database.cities

import androidx.room.*
import com.example.weathercheck2000.database.cities.Cities
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao{
    @Query("SELECT * FROM cities")
    fun getAll(): Flow<MutableList<Cities>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(cities: Cities)

    @Delete
    fun deleteCity(cities: Cities)
    // it is crazy that it needs to run 4 functions to delete a city to actually get there :D maybe
    // this can be shortcuted
    // view -> CityDetailFragment -> CitiesViewModel -> Repository -> Dao! LOL
    // TODO

    /*
   @Query("INSERT INTO cities (name,lat,lon) VALUES (:name,:lat,:lon)")
   fun insertCity(name: String, lat: String, lon: String)
   */
}