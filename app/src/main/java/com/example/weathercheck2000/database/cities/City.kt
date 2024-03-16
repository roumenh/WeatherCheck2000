package com.example.weathercheck2000.database.cities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City (
    // Data class to hold class for my Database structure
    @PrimaryKey(autoGenerate = true)       val id: Int = 0,
    @ColumnInfo(name = "name")    val name: String,
    @ColumnInfo(name = "lat")     val lat: String,
    @ColumnInfo(name = "lon")     val lon: String


)