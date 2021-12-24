package com.udacity.asteroidradar.api

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.model.AsteroidFeedResponse
import com.udacity.asteroidradar.model.AsteroidImageOfTheDayResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


interface NasaService {
    @GET("neo/rest/v1/feed")
    suspend fun asteroidsFeedRequest(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<AsteroidFeedResponse>

    @GET("planetary/apod")
    suspend fun imageOfTheDayRequest(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<AsteroidImageOfTheDayResponse>
}

fun getFormattedDay(currentTime: Date = Calendar.getInstance().time): String {
    return SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).format(currentTime)
}

class PictureOfTheDayService private constructor() {
    suspend fun getPictureOfTheDayResponse(): AsteroidImageOfTheDayResponse? {
        Log.i("getPictureOfTheDay", "FKFKF")
        val interceptor = HttpLoggingInterceptor()

        val client = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()
        val retrofit: Retrofit = Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(Constants.BASE_URL)
            .build()

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.build()

        val service = retrofit.create(NasaService::class.java)
        try {
            val result = service.imageOfTheDayRequest()

            return if (result.code() >= 400) {
                Log.i("getPictureOfTheDay", result.toString())
                println("server error, ${result.code()}, ${result.errorBody()}")
                null
            } else {
                Log.i("getPictureOfTheDay else", result.toString())
                var res: AsteroidImageOfTheDayResponse? = null
                withContext(Dispatchers.IO) {
                    val item = AsteroidImageOfTheDayResponse(
                        result.body()?.title.toString(),
                        result.body()?.url.toString(),
                        result.body()?.media_type.toString()
                    )
                    if (item != null) {
                        res = item
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
        val instance: PictureOfTheDayService by lazy {
            PictureOfTheDayService()
        }
    }
}

class AsteroidFeedResponseModelItemService private constructor() {
    suspend fun getFeedResponse(): List<AsteroidFeedResponse.AsteroidFeedResponseModelItem>? {
        val interceptor = HttpLoggingInterceptor()


        val client = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()
        val retrofit: Retrofit = Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(Constants.BASE_URL)
            .build()

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.build()

        val service = retrofit.create(NasaService::class.java)

        try {
            val endCalendar = Calendar.getInstance()
            endCalendar.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
            val result =
                service.asteroidsFeedRequest(getFormattedDay(), getFormattedDay(endCalendar.time))

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