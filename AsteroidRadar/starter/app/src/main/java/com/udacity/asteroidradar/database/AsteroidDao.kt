package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAsteroid(asteroid: Asteroid)

    @Query("SELECT * FROM asteroid ORDER BY id DESC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroid")
    suspend fun deleteAllAsteroids()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(todayImageResponseModel: TodayImageResponseModel)

    @Query("SELECT * FROM image")
    fun getImage(): LiveData<TodayImageResponseModel>

    @Query("DELETE FROM image")
    suspend fun deleteImage()
}