package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val asteroidRepository = AsteroidRepository.get()
    suspend fun getAsteroidsListLiveData() : LiveData<List<Asteroid>> = asteroidRepository.getAsteroidsListLiveData()
    suspend fun getImageOfTheDayLiveData() = asteroidRepository.getImageOfTheDayLiveData()
}