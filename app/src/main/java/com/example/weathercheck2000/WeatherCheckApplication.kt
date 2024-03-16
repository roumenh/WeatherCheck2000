package com.example.weathercheck2000

import android.app.Application
import com.example.weathercheck2000.data.repository.CitiesRepositoryImpl
import com.example.weathercheck2000.database.AppDatabase

class WeatherCheckApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val citiesRepository by lazy { CitiesRepositoryImpl(database.citiesDao()) } //TODO - ehm, interesting. probably can bee deleted after introducing DI
}