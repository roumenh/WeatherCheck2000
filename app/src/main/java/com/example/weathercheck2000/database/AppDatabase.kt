package com.example.weathercheck2000.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weathercheck2000.database.cities.Cities
import com.example.weathercheck2000.database.cities.CitiesDao

@Database(entities = arrayOf(Cities::class), version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun citiesDao(): CitiesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .createFromAsset("database/cities.db")
                    .build()
                INSTANCE = instance

                instance

            }
        }
    }

}