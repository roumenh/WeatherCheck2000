package com.example.weathercheck2000.database.cities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao{
    @Query("SELECT * FROM city")
    fun getAll(): Flow<MutableList<City>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(city: City)

    @Delete
    fun deleteCity(city: City)
    // it is crazy that it needs to run 4 functions to delete a city to actually get there :D maybe
    // this can be shortcuted
    // view -> CityDetailFragment -> CitiesViewModel -> Repository -> Dao! LOL
    // TODO

    /*
   @Query("INSERT INTO cities (name,lat,lon) VALUES (:name,:lat,:lon)")
   fun insertCity(name: String, lat: String, lon: String)
   */
}