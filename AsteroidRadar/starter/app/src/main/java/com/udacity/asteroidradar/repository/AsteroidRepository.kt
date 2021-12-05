package com.udacity.asteroidradar.repository

import android.util.Log
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaService
import com.udacity.asteroidradar.models.AsteroidFeedResponseModelAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val DATABASE_NAME = "asteroid-database"

class AsteroidRepository(private val service: NasaService) {
//    val moshi: Moshi = Moshi.Builder().add(AsteroidFeedResponseModelAdapter()).build()
//    val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(Constants.BASE_URL)
//        .addConverterFactory(MoshiConverterFactory.create(moshi))
//        .build()
//    val nasaApi: NasaApi = retrofit.create(NasaApi::class.java)

    suspend fun getAsteroidsFeedList() = service.asteroidsFeedRequest()
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

//    private val retrofit: Api = Retrofit.Builder()
//        .baseUrl(Constants.BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(Api::class.java)
//
//    suspend fun getAsteroidsList(): Flow<List<Asteroid>>? {
//        val response = retrofit.asteroidsRequest()
//        return if (response.isSuccessful) response.body()?.let {
//            database.asteroidDao().deleteAllAsteroids()
//            it.nearEarthObjects.values.forEach { list ->
//                list.forEach { asteroid ->
//                    database.asteroidDao().addAsteroid(asteroid)
//                }
//            }
//            database.asteroidDao().getAllAsteroids()
//        }
//        else database.asteroidDao().getAllAsteroids()
//    }
//
//    suspend fun getImageOfTheDay(): Flow<TodayImageResponseModel>? {
//        val response = retrofit.planetaryRequest()
//        return if (response.isSuccessful) response.body()?.let {
//            database.asteroidDao().deleteImage()
//            database.asteroidDao().addImage(it)
//            database.asteroidDao().getImage()
//        } else database.asteroidDao().getImage()
//    }