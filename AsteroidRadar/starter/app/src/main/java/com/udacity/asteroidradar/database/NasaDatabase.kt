package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay

@Database(entities = arrayOf(Asteroid::class, PictureOfDay::class), version = 1)
abstract class NasaDatabase : RoomDatabase() {
    abstract fun nasaDao(): NasaDao
    companion object {
        private var instance: NasaDatabase? = null
        fun getInstance(context: Context): NasaDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NasaDatabase::class.java,
                    "NasaDatabase").build()
            }
            return instance as NasaDatabase
        }
    } }