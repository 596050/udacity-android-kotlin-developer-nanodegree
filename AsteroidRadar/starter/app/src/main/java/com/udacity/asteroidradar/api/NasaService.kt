package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.model.AsteroidFeedResponse
import com.udacity.asteroidradar.model.AsteroidImageOfTheDayResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.w3c.dom.Node
import retrofit2.http.Headers
import retrofit2.http.Url
import java.util.concurrent.TimeUnit
import javax.xml.parsers.DocumentBuilderFactory

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

class AsteroidFeedResponseModelItemService private constructor() {
    suspend fun getFeedResponse(): List<AsteroidFeedResponse.AsteroidFeedResponseModelItem>? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        client.build()

        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()
        val retrofit: Retrofit = Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(Constants.BASE_URL)
            .build()

        val service = retrofit.create(NasaService::class.java)

        try {
            val result = service.asteroidsFeedRequest()
            return if (result.code() >= 400) {
                println("server error, ${result.code()}, ${result.errorBody()}")
                null
            } else {
                var res: List<AsteroidFeedResponse.AsteroidFeedResponseModelItem> = mutableListOf()
                withContext(Dispatchers.IO) {
                    val items = result.body()?.near_earth_objects?.values?.first()
                    if (items != null) {
                        res = items
                    }
                }
                res
            }
        } catch (t: Throwable) {
            println("error, ${t.localizedMessage}")
        }
        return null
    }
    companion object {
        val instance: AsteroidFeedResponseModelItemService by lazy {
            AsteroidFeedResponseModelItemService()
        }
    }
}