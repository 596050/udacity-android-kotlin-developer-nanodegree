package com.udacity.asteroidradar.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.NetworkResult
import com.udacity.asteroidradar.models.AsteroidFeed
import com.udacity.asteroidradar.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** RETROFIT */
    var asteroidsResponse: MutableLiveData<NetworkResult<AsteroidFeed>> = MutableLiveData()
//    var searchedRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
//    var foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    fun getAsteroids() = viewModelScope.launch {
        getAsteroidsCall()
    }

    private suspend fun getAsteroidsCall() {
        asteroidsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getAsteroids()
                asteroidsResponse.value = handleAsteroidsResponse(response)
//                val asteroids = asteroidsResponse.value!!.data
//                if (foodRecipe != null) {
//                    offlineCacheRecipes(foodRecipe)
//                }
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
//
//    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
//        searchRecipesSafeCall(searchQuery)
//    }
//
//    fun getFoodJoke(apiKey: String) = viewModelScope.launch {
//        getFoodJokeSafeCall(apiKey)
//    }
//
//    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
//        searchedRecipesResponse.value = NetworkResult.Loading()
//        if (hasInternetConnection()) {
//            try {
//                val response = repository.remote.searchRecipes(searchQuery)
//                searchedRecipesResponse.value = handleFoodRecipesResponse(response)
//            } catch (e: Exception) {
//                searchedRecipesResponse.value = NetworkResult.Error("Recipes not found.")
//            }
//        } else {
//            searchedRecipesResponse.value = NetworkResult.Error("No Internet Connection.")
//        }
//    }
//
//    private suspend fun getFoodJokeSafeCall(apiKey: String) {
//        foodJokeResponse.value = NetworkResult.Loading()
//        if (hasInternetConnection()) {
//            try {
//                val response = repository.remote.getFoodJoke(apiKey)
//                foodJokeResponse.value = handleFoodJokeResponse(response)
//
//                val foodJoke = foodJokeResponse.value!!.data
//                if(foodJoke != null){
//                    offlineCacheFoodJoke(foodJoke)
//                }
//            } catch (e: Exception) {
//                foodJokeResponse.value = NetworkResult.Error("Recipes not found.")
//            }
//        } else {
//            foodJokeResponse.value = NetworkResult.Error("No Internet Connection.")
//        }
//    }
//
//    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
//        val recipesEntity = RecipesEntity(foodRecipe)
//        insertRecipes(recipesEntity)
//    }
//
//    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
//        val foodJokeEntity = FoodJokeEntity(foodJoke)
//        insertFoodJoke(foodJokeEntity)
//    }
//
//
//    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {
//        return when {
//            response.message().toString().contains("timeout") -> {
//                NetworkResult.Error("Timeout")
//            }
//            response.code() == 402 -> {
//                NetworkResult.Error("API Key Limited.")
//            }
//            response.isSuccessful -> {
//                val foodJoke = response.body()
//                NetworkResult.Success(foodJoke!!)
//            }
//            else -> {
//                NetworkResult.Error(response.message())
//            }
//        }
//    }
//

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