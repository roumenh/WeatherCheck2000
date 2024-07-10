package com.example.weathercheck2000.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weathercheck2000.database.cities.CitiesDao
import com.example.weathercheck2000.database.cities.City
import com.example.weathercheck2000.database.collectibles.Collectible

@Database(entities = [City::class, Collectible::class], version = 3)
abstract class AppDatabase: RoomDatabase(){

    abstract fun citiesDao(): CitiesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database")
                    .fallbackToDestructiveMigration()
                   // .createFromAsset("database/cities.db")
                    .build()
                INSTANCE = instance

                instance

            }
        }

    }

}