package com.example.weathercheck2000

import android.app.Application
import com.example.weathercheck2000.di.appModule
import com.example.weathercheck2000.di.databaseModule
import com.example.weathercheck2000.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(appModule, networkModule, databaseModule)
        }
    }

}