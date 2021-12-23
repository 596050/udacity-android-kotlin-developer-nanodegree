package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.udacity.asteroidradar.worker.AsteroidWorkerManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scheduleJobs()
    }

    private fun scheduleJobs() {
        val constraints: Constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
            setRequiresCharging(true)
        }.build()

        val request = PeriodicWorkRequestBuilder<AsteroidWorkerManager>(
            1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork("com.udacity.asteroidradar.asteroids",
            ExistingPeriodicWorkPolicy.REPLACE, request)
    }
}