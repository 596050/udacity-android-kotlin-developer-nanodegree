package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.model.AsteroidFeedResponse
import com.udacity.asteroidradar.model.AsteroidImageOfTheDayResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {
    @GET("neo/rest/v1/feed")
    suspend fun asteroidsFeedRequest(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<AsteroidFeedResponse>

        @GET("planetary/apod")
    fun imageOfTheDayRequest(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<AsteroidImageOfTheDayResponse>
}


