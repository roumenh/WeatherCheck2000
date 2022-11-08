package com.example.weathercheck2000.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weathercheck2000.database.cities.Cities
import com.example.weathercheck2000.database.cities.CitiesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Cities::class], version = 2)
abstract class AppDatabase: RoomDatabase(){

    abstract fun citiesDao(): CitiesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database")
                    .fallbackToDestructiveMigration()
                    //.createFromAsset("database/cities.db")
                    .build()
                INSTANCE = instance

                instance

            }
        }

    }

}