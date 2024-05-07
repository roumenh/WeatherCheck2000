package com.example.weathercheck2000.di

import android.content.Context
import com.example.weathercheck2000.data.repository.CitiesRepository
import com.example.weathercheck2000.data.repository.CitiesRepositoryImpl
import com.example.weathercheck2000.data.repository.MeteoInfoRepository
import com.example.weathercheck2000.data.repository.MeteoInfoRepositoryImpl
import com.example.weathercheck2000.database.AppDatabase
import com.example.weathercheck2000.database.cities.CitiesDao
import com.example.weathercheck2000.ui.addCity.AddCityViewModel
import com.example.weathercheck2000.ui.cityDetail.CityDetailViewModel
import com.example.weathercheck2000.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


// --------- Data base ---------------
fun provideDataBase(context: Context): AppDatabase =
    AppDatabase.getDatabase(context)

fun provideDao(appDataBase: AppDatabase): CitiesDao = appDataBase.citiesDao()


val databaseModule= module {
    single { provideDataBase(androidContext()) }
    single { provideDao(get()) }
}

val appModule = module {

    single<MeteoInfoRepository> { MeteoInfoRepositoryImpl() }
    single<CitiesRepository> { CitiesRepositoryImpl(get()) }

    viewModel { CityDetailViewModel(get()) }
    viewModel { AddCityViewModel(get()) }
    viewModel { HomeViewModel(get()) }

}

val networkModule = module {

}