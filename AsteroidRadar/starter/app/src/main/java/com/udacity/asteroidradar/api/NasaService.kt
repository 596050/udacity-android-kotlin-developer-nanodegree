package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.model.AsteroidFeedResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface NasaService {
    @GET("neo/rest/v1/feed")
    suspend fun asteroidsFeedRequest(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<AsteroidFeedResponse>

    companion object {
        val instance: NasaService by lazy {
            val json = Json {
                ignoreUnknownKeys = true
            }
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            val contentType = "application/json".toMediaType()
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
             retrofit.create(NasaService::class.java)
        }

    }
}


//    @GET("planetary/apod")
//    fun imageOfTheDayRequest(
//        @Query("api_key") apiKey: String = API_KEY
//    ): AsteroidImageOfTheDayResponseModel