package com.udacity.asteroidradar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaService
import com.udacity.asteroidradar.database.NasaDao
import com.udacity.asteroidradar.database.NasaDatabase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class AsteroidRepository(context: Context) {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private val contentType = "application/json".toMediaType()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build().create(NasaService::class.java)

    private val db = NasaDatabase.getInstance(context)
    private val nasaDao: NasaDao = db.nasaDao()


    suspend fun getAsteroidsFeedList(): LiveData<List<Asteroid>>? {
        val results = retrofit.asteroidsFeedRequest()
        if (results.isSuccessful) results.body()?.let {
            nasaDao.deleteAllAsteroids()
            it.near_earth_objects.values.toList().get(0).map { item ->
                val asteroid = Asteroid(
                    item.id.toLong(),
                    codename = item.name,
                    closeApproachDate = item.close_approach_data[0].close_approach_date,
                    absoluteMagnitude = item.absolute_magnitude_h,
                    estimatedDiameter = item.estimated_diameter.kilometers.estimated_diameter_max,
                    relativeVelocity = item.close_approach_data[0].relative_velocity.kilometers_per_second.toDouble(),
                    distanceFromEarth = item.close_approach_data[0].miss_distance.astronomical.toDouble(),
                    isPotentiallyHazardous = item.is_potentially_hazardous_asteroid
                )
                nasaDao.createAsteroid(asteroid)
            }
        }
        return nasaDao.getAsteroids()
    }

//    fun addBookmark(bookmark: Bookmark): Long? {
//        val newId = bookmarkDao.insertBookmark(bookmark)
//        bookmark.id = newId
//        return newId
//    }
//    fun createBookmark(): Bookmark {
//        return Bookmark()
//    }
//    val allBookmarks: LiveData<List<Bookmark>>
//        get() {
//            return bookmarkDao.loadAll()
//        }
}

//    suspend fun getAsteroidImageOfTheDayLiveData(): LiveData<AsteroidImageOfTheDayResponseModel>? {
//        val response = nasaApi.planetaryRequest()
//        return if (response.isSuccessful) response.body()?.let {
//            database.asteroidDao().deleteImage()
//            database.asteroidDao().addImage(it)
//            database.asteroidDao().getImage()
//        } else database.asteroidDao().getImage()
//    }
