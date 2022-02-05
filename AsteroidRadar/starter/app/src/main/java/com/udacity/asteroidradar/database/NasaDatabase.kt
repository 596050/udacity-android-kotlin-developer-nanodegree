package com.udacity.asteroidradar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidTypeConverter
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.models.AsteroidFeedResponseModelItem

@Database(
    entities = [Asteroid::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AsteroidTypeConverter::class)
abstract class AsteroidsDatabase: RoomDatabase() {
    abstract fun nasaDao(): NasaDao

}


