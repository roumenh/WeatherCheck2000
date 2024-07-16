package com.example.weathercheck2000.database.collectibles

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Collectible(
    // Data class for collection of robin photo
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val code: Int,
    val dateCollected: Long,
)