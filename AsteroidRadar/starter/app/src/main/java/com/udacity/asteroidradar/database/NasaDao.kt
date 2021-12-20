package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.model.AsteroidImageOfTheDayResponse

@Dao
abstract class NasaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun createAsteroid(asteroid: Asteroid)

    @Query("SELECT * FROM Asteroid ORDER BY id DESC")
    abstract fun getAsteroids(): LiveData<List<Asteroid>>

    @Query("DELETE FROM Asteroid")
    abstract fun deleteAllAsteroids()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun createPictureOfTheDay(todayImageResponseModel: PictureOfDay)

    @Query("SELECT * FROM PictureOfDay")
    abstract fun getPictureOfTheDay(): LiveData<AsteroidImageOfTheDayResponse>
}
