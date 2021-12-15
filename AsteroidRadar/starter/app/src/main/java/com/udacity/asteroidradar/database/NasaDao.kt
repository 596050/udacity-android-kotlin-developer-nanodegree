package com.udacity.asteroidradar.database

import android.graphics.Picture
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.model.AsteroidImageOfTheDayResponse

@Dao
interface NasaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAsteroid(asteroid: Asteroid)

    @Query("SELECT * FROM Asteroid ORDER BY id DESC")
    fun getAsteroids(): LiveData<List<Asteroid>>

    @Query("DELETE FROM Asteroid")
    suspend fun deleteAllAsteroids()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPictureOfTheDay(todayImageResponseModel: PictureOfDay)

    @Query("SELECT * FROM PictureOfDay")
    fun getPictureOfTheDay(): LiveData<AsteroidImageOfTheDayResponse>
}
