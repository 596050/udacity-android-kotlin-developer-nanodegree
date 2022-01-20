package com.udacity.asteroidradar.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

//@ActivityRetainedScoped
@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
//    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
//    val local = localDataSource
}

//import androidx.lifecycle.LiveData
//import com.udacity.asteroidradar.Asteroid
//import com.udacity.asteroidradar.api.AsteroidFeedResponseModelItemService
//import com.udacity.asteroidradar.database.NasaDao
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//
//class AsteroidRepository(
//    private val nasaDao: NasaDao,
//    private val asteroidFeedService: AsteroidFeedResponseModelItemService
//) {
//    fun getAsteroidsFeedList(): LiveData<List<Asteroid>> = nasaDao.getAsteroids()
//
//    fun save() {
//        GlobalScope.launch {
//            val feedResponse = asteroidFeedService.getFeedResponse()
//            if (feedResponse != null) {
//                nasaDao.deleteAllAsteroids()
//                feedResponse.map { item ->
//                    val asteroid = Asteroid(
//                        item.id.toLong(),
//                        codename = item.name,
//                        closeApproachDate = item.close_approach_data[0].close_approach_date,
//                        absoluteMagnitude = item.absolute_magnitude_h,
//                        estimatedDiameter = item.estimated_diameter.kilometers.estimated_diameter_max,
//                        relativeVelocity = item.close_approach_data[0].relative_velocity.kilometers_per_second.toDouble(),
//                        distanceFromEarth = item.close_approach_data[0].miss_distance.astronomical.toDouble(),
//                        isPotentiallyHazardous = item.is_potentially_hazardous_asteroid
//                    )
//                    nasaDao.createAsteroid(asteroid)
//                }
//            }
//        }
//    }

//    companion object {
//        @Volatile
//        private var instance: AsteroidRepository? = null
//        fun getInstance(nasaDao: NasaDao) =
//            this.instance ?: synchronized(this) {
//                instance ?: AsteroidRepository(nasaDao)?.also {
//                    instance = it
//                }
//            }
//    }
//}

//class AsteroidRepository(context: Context) {
//    private val json = Json {
//        ignoreUnknownKeys = true
//    }
//    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(logging)
//        .build()
//    private val contentType = "application/json".toMediaType()
//    private val retrofit = Retrofit.Builder()
//        .client(client)
//        .addConverterFactory(json.asConverterFactory(contentType))
//        .baseUrl(Constants.BASE_URL)
//        .build()
//        .create(NasaService::class.java)
//
//    private val db = NasaDatabase.getInstance(context)
//    private val nasaDao: NasaDao = db.nasaDao()
//
//    suspend fun getAsteroidsFeedList(): LiveData<List<Asteroid>>? {
//        // TODO: Fix Socket timeout error
//        val results = retrofit.asteroidsFeedRequest()
//        if (results.isSuccessful) results.body()?.let {
//            nasaDao.deleteAllAsteroids()
//            it.near_earth_objects.values.toList()[0].map { item ->
//                val asteroid = Asteroid(
//                    item.id.toLong(),
//                    codename = item.name,
//                    closeApproachDate = item.close_approach_data[0].close_approach_date,
//                    absoluteMagnitude = item.absolute_magnitude_h,
//                    estimatedDiameter = item.estimated_diameter.kilometers.estimated_diameter_max,
//                    relativeVelocity = item.close_approach_data[0].relative_velocity.kilometers_per_second.toDouble(),
//                    distanceFromEarth = item.close_approach_data[0].miss_distance.astronomical.toDouble(),
//                    isPotentiallyHazardous = item.is_potentially_hazardous_asteroid
//                )
//                nasaDao.createAsteroid(asteroid)
//            }
//        }
//        return nasaDao.getAsteroids()
//    }
//}

//    suspend fun getAsteroidImageOfTheDayLiveData(): LiveData<AsteroidImageOfTheDayResponseModel>? {
//        val response = nasaApi.planetaryRequest()
//        return if (response.isSuccessful) response.body()?.let {
//            database.asteroidDao().deleteImage()
//            database.asteroidDao().addImage(it)
//            database.asteroidDao().getImage()
//        } else database.asteroidDao().getImage()
//    }
