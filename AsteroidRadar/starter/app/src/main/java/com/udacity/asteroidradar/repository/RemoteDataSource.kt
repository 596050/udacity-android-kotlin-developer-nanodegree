package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.models.AsteroidFeed
import com.udacity.asteroidradar.network.NasaApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val asteroidAPI: NasaApi
) {
    suspend fun getAsteroids(): Response<AsteroidFeed> {
        return asteroidAPI.asteroidsFeedRequest()
    }

    suspend fun getImageOfTheDay(): Response<AsteroidFeed> {
        return asteroidAPI.asteroidsFeedRequest()
    }
}
