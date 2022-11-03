package com.example.weathercheck2000

import android.app.Application
import com.example.weathercheck2000.database.AppDatabase

class WeatherCheckApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}