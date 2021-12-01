package com.udacity.asteroidradar.main

import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val asteroidRepository = AsteroidRepository.get()
    suspend fun getAsteroidsListLiveData() = asteroidRepository.getAsteroidsListLiveData()
    suspend fun getImageOfTheDayLiveData() = asteroidRepository.getImageOfTheDayLiveData()
}