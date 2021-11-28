package com.udacity.asteroidradar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.Asteroid

@Database(entities = [Asteroid::class], version = 1)
@TypeConverters(AsteroidTypeConverter::class)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDao
}