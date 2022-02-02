package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.models.AsteroidFeed
import com.udacity.asteroidradar.models.AsteroidFeedResponseModelItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NasaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroid: Asteroid)

    @Query("SELECT * FROM Asteroid ORDER BY id DESC")
    fun readAsteroids(): Flow<List<Asteroid>>

    @Query("DELETE FROM Asteroid")
    fun deleteAllAsteroids()

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun createPictureOfTheDay(todayImageResponseModel: PictureOfDay)
//
//    @Query("SELECT * FROM PictureOfDay")
//    fun getPictureOfTheDay(): LiveData<PictureOfDay>
}
