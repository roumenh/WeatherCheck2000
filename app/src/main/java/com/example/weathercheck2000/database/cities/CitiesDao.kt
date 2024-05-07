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

}