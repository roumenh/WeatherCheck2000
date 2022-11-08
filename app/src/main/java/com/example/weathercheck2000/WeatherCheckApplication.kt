package com.example.weathercheck2000

import CitiesRepository
import android.app.Application
import com.example.weathercheck2000.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WeatherCheckApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { CitiesRepository(database.citiesDao())}
}