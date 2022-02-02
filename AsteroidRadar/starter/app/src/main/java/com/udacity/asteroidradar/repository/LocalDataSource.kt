package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.NasaDao
import com.udacity.asteroidradar.models.AsteroidFeedResponseModelItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val asteroidsDao: NasaDao
) {
     fun readDatabase(): Flow<List<Any>> {
         return asteroidsDao.readAsteroids()
     }

    suspend fun insertAsteroids(entity: Asteroid) {
        asteroidsDao.insertAsteroids(entity)
    }
}