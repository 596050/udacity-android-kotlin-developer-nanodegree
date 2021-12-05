package com.udacity.asteroidradar.api

import android.util.Log
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.models.AsteroidFeedResponse
import com.udacity.asteroidradar.models.AsteroidFeedResponseModelAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {
    @GET("neo/rest/v1/feed")
    suspend fun asteroidsFeedRequest(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<AsteroidFeedResponse>

    companion object {
        val instance: NasaService by lazy {
            Log.println(Log.INFO, "lllll", "KKKKK")
            val moshi: Moshi = Moshi.Builder().add(AsteroidFeedResponseModelAdapter()).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
             retrofit.create(NasaService::class.java)

        }
    }
}


//    @GET("planetary/apod")
//    fun imageOfTheDayRequest(
//        @Query("api_key") apiKey: String = API_KEY
//    ): AsteroidImageOfTheDayResponseModel