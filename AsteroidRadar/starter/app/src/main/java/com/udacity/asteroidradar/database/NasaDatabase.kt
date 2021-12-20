package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Asteroid::class, PictureOfDay::class], version = 1)
abstract class NasaDatabase : RoomDatabase() {
    abstract fun nasaDao(): NasaDao

    companion object {
        @Volatile
        private var Instance: NasaDatabase? = null
        fun getDatabase(
            context: Context, scope: CoroutineScope
        ): NasaDatabase =
            Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    NasaDatabase::class.java,
                    "NasaDatabase"
                ).build()
                Instance = instance
                instance
            }
    }
}