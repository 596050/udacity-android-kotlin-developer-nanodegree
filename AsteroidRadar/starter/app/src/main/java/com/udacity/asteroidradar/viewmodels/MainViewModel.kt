package com.udacity.asteroidradar.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.NetworkResult
import com.udacity.asteroidradar.models.AsteroidFeed
import com.udacity.asteroidradar.models.AsteroidFeedResponseModelItem
import com.udacity.asteroidradar.models.AsteroidImageOfTheDayResponse
import com.udacity.asteroidradar.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


/** ROOM DATABASE */
    val readAsteroids: LiveData<List<Any>> = repository.local.readDatabase().asLiveData()
    private fun insertAsteroid(asteroid: Asteroid) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertAsteroids(asteroid)
    }

    /** RETROFIT */
    var asteroidsResponse: MutableLiveData<NetworkResult<AsteroidFeed>> = MutableLiveData()
    var imageOfTheDayResponse: MutableLiveData<NetworkResult<AsteroidImageOfTheDayResponse>> = MutableLiveData()

    fun getAsteroids() = viewModelScope.launch {
        getAsteroidsCall()
    }

    fun getImageOfTheDay() = viewModelScope.launch {
        getImageOfTheDayCall()
    }

    private suspend fun getImageOfTheDayCall() {
        imageOfTheDayResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getImageOfTheDay()
                imageOfTheDayResponse.value = handleImageOfTheDayResponse(response)
            } catch (e: Exception) {
                imageOfTheDayResponse.value = NetworkResult.Error("Asteroids not found.")
            }
        } else {
            imageOfTheDayResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleImageOfTheDayResponse(response: Response<AsteroidImageOfTheDayResponse>): NetworkResult<AsteroidImageOfTheDayResponse>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.url.isNullOrEmpty() -> {
                return NetworkResult.Error("Asteroids not found.")
            }
            response.isSuccessful -> {
                val responseBody = response.body()
                return NetworkResult.Success(responseBody!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private suspend fun getAsteroidsCall() {
        asteroidsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getAsteroids()
                asteroidsResponse.value = handleAsteroidsResponse(response)
                val asteroids = asteroidsResponse.value!!.data
                if (asteroids != null) {
                    offlineCacheRecipes(asteroids)
                }
            } catch (e: Exception) {
                asteroidsResponse.value = NetworkResult.Error("Asteroids not found.")
            }
        } else {
            asteroidsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleAsteroidsResponse(response: Response<AsteroidFeed>): NetworkResult<AsteroidFeed>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.near_earth_objects.isNullOrEmpty() -> {
                return NetworkResult.Error("Asteroids not found.")
            }
            response.isSuccessful -> {
                val asteroids = response.body()
                return NetworkResult.Success(asteroids!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun offlineCacheRecipes(asteroids: AsteroidFeed) {
        val asteroidList = asteroids.near_earth_objects?.toList()[0]
        asteroidList.second.forEach {
            val entity = Asteroid(
                id=it.id.toLong(),
                codename=it.name,
                        closeApproachDate=it.close_approach_data[0].close_approach_date,
                        absoluteMagnitude=it.absolute_magnitude_h,
                        estimatedDiameter=it.estimated_diameter.kilometers.estimated_diameter_max,
                        relativeVelocity=it.close_approach_data[0].relative_velocity.kilometers_per_second.toDouble(),
                        distanceFromEarth=it.close_approach_data[0].miss_distance.astronomical.toDouble(),
                        isPotentiallyHazardous=it.is_potentially_hazardous_asteroid,
                )
            Log.i("entity", entity.toString())
            insertAsteroid(entity)
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
//
//    var recyclerViewState: Parcelable? = null
//
//    /** ROOM DATABASE */
//
//    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
//    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipes().asLiveData()
//    val readFoodJoke: LiveData<List<FoodJokeEntity>> = repository.local.readFoodJoke().asLiveData()
//
//    private fun insertRecipes(recipesEntity: RecipesEntity) =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.local.insertRecipes(recipesEntity)
//        }
//
//    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.local.insertFavoriteRecipes(favoritesEntity)
//        }
//
//    private fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.local.insertFoodJoke(foodJokeEntity)
//        }
//
//    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.local.deleteFavoriteRecipe(favoritesEntity)
//        }
//
//    fun deleteAllFavoriteRecipes() =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.local.deleteAllFavoriteRecipes()
//        }
//
}