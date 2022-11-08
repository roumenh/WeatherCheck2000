package com.example.weathercheck2000.database.cities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathercheck2000.database.cities.Cities
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao{
    @Query("SELECT * FROM cities")
    fun getAll(): Flow<MutableList<Cities>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(cities: Cities)

    /*
   @Query("INSERT INTO cities (name,lat,lon) VALUES (:name,:lat,:lon)")
   fun insertCity(name: String, lat: String, lon: String)
   */
}