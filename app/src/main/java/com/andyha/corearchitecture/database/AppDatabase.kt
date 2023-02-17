package com.andyha.corearchitecture.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andyha.featureweatherkit.data.dao.LocationDetectedDao
import com.andyha.featureweatherkit.data.dao.WeatherDao
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import com.andyha.featureweatherkit.data.model.Weather

const val VERSION = 1

@Database(
    version = VERSION,
    entities = [
        Weather::class,
        LocationDetected::class
    ],
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    StringArrayConverter::class,
    StringToLongConverter::class,
)

abstract class AppDatabase : RoomDatabase() {

    fun clearAll() = clearAllTables()

    //define DAO get method here...
    abstract fun getWeatherDao(): WeatherDao
    abstract fun getLocationDetectedDao(): LocationDetectedDao

    companion object {
        private const val DATABASE_NAME = "Core-Arch-DB"

        fun getInstance(context: Context): AppDatabase {
            val builder = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
            return builder.build()
        }
    }
}