package com.udacity.asteroidradar

import android.app.Application
import com.udacity.asteroidradar.repository.AsteroidRepository

class AsteroidIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AsteroidRepository.initialize(this)
    }
}