package com.udacity.asteroidradar.repository

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val DATABASE_NAME = "asteroid-database"

class AsteroidRepository() {
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

    suspend fun getAsteroidsFeedList():  List<Asteroid>? {
        val results = retrofit.asteroidsFeedRequest()
        val asteroids = results.body()?.near_earth_objects?.values?.toList()?.get(0)?.map{
            Asteroid(
                it.id.toLong(),
                codename=it.name,
                closeApproachDate=it.close_approach_data[0].close_approach_date,
                absoluteMagnitude=it.absolute_magnitude_h,
                estimatedDiameter=it.estimated_diameter.kilometers.estimated_diameter_max,
                relativeVelocity=it.close_approach_data[0].relative_velocity.kilometers_per_second.toDouble(),
                distanceFromEarth=it.close_approach_data[0].miss_distance.astronomical.toDouble(),
                isPotentiallyHazardous=it.is_potentially_hazardous_asteroid
            )
        }
        return asteroids
    }
//    fun getAsteroidsFeedList(): List<AsteroidFeedResponseModelItem> {
////        val response = nasaApi.asteroidsFeedRequest()
////        Log.i("getAsteroidsFeedList", response.toString())
//
//        return arrayListOf()
//
////       if (response) response.body()?.let {
////            database.asteroidDao().deleteAllAsteroids()
////            it.nearEarthObjects.values.forEach { list ->
////                list.forEach { asteroid ->
////                    database.asteroidDao().addAsteroid(asteroid)
////                }
////            } ?: emptyList<Asteroid>()
////            database.asteroidDao().getAllAsteroids()
////        }
////        else database.asteroidDao().getAllAsteroids()
////        return MutableLiveData<List<Asteroid>>()
//    }
//
//    suspend fun getAsteroidImageOfTheDayLiveData(): LiveData<AsteroidImageOfTheDayResponseModel>? {
//        val response = nasaApi.planetaryRequest()
//        return if (response.isSuccessful) response.body()?.let {
//            database.asteroidDao().deleteImage()
//            database.asteroidDao().addImage(it)
//            database.asteroidDao().getImage()
//        } else database.asteroidDao().getImage()
//    }
}
