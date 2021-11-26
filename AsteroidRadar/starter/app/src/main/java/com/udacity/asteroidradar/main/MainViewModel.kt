package com.udacity.asteroidradar.main

import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import kotlin.random.Random

class MainViewModel : ViewModel() {
    val asteroids = mutableListOf<Asteroid>()
    init {
        for (i in 0 until 100) {
            val closeApproachDate = "2020-08-01"
            val isPotentiallyHazardous = i % 2 == 0;
            val asteroid = Asteroid(
                id = i as Long,
                codename = "Asteroid #$i",
                closeApproachDate = closeApproachDate,
                absoluteMagnitude = i % 2.0,
                estimatedDiameter = Random.nextDouble(),
                relativeVelocity = Random.nextDouble(),
                distanceFromEarth = Random.nextDouble(),
                isPotentiallyHazardous = isPotentiallyHazardous
            );
            asteroids += asteroid
        }
    }
}