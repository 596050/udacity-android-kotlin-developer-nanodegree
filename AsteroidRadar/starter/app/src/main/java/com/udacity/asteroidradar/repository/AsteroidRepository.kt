package com.udacity.asteroidradar.repository

import android.content.Context
import android.icu.text.AlphabeticIndex
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import retrofit2.Retrofit

private const val DATABASE_NAME = "asteroid-database"

class AsteroidRepository private constructor(context: Context) {
    private val database: AsteroidDatabase = Room.databaseBuilder(
        context.applicationContext,
        AsteroidDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val asteroidDao = database.asteroidDao()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .build()
    val nasaApi: NasaApi = retrofit.create(NasaApi::class.java)

    companion object {
        private var INSTANCE: AsteroidRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = AsteroidRepository(context)
            }
        }

        fun get(): AsteroidRepository {
            return INSTANCE
                ?: throw IllegalAccessException("Must initialize ${this.javaClass.simpleName}")
        }
    }

    suspend fun getAsteroidsListLiveData(): LiveData<List<Asteroid>> {
        val response = nasaApi.asteroidsRequest()
        val asteroidList = if (response.isSuccessful) response.body()?.let {
            database.asteroidDao().deleteAllAsteroids()
            it.nearEarthObjects.values.forEach { list ->
                list.forEach { asteroid ->
                    database.asteroidDao().addAsteroid(asteroid)
                }
            } ?: emptyList<Asteroid>()
            database.asteroidDao().getAllAsteroids()
        }
        else database.asteroidDao().getAllAsteroids()
        return MutableLiveData<List<Asteroid>>()
    }

    suspend fun getImageOfTheDayLiveData(): LiveData<TodayImageResponseModel>? {
        val response = retrofit.planetaryRequest()
        return if (response.isSuccessful) response.body()?.let {
            database.asteroidDao().deleteImage()
            database.asteroidDao().addImage(it)
            database.asteroidDao().getImage()
        } else database.asteroidDao().getImage()
    }
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
