package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.Constants.Companion.API_KEY
import com.udacity.asteroidradar.models.AsteroidFeed
import com.udacity.asteroidradar.models.AsteroidImageOfTheDayResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("/neo/rest/v1/feed")
    suspend fun asteroidsFeedRequest(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<AsteroidFeed>

    @GET("/planetary/apod")
    suspend fun imageOfTheDayRequest(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<AsteroidImageOfTheDayResponse>
}