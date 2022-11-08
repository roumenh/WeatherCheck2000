package com.example.weathercheck2000.database.cities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cities (
    // Data class to hold class for my Database structure
    @PrimaryKey(autoGenerate = true)       val id: Int = 0,
    @NonNull @ColumnInfo(name = "name")    val name: String,
    @NonNull @ColumnInfo(name = "lat")     val lat: String,
    @NonNull @ColumnInfo(name = "lon")     val lon: String


)