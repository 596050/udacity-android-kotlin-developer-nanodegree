package com.udacity.asteroidradar.api


import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("neo/rest/v1/feed")
    fun asteroidsRequest(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<AsteroidFeedResponseModel>

    @GET("planetary/apod")
    fun planetaryRequest(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<TodayImageResponseModel>
}