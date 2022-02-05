package com.udacity.asteroidradar.worker

import android.content.Context
import android.util.Log
//import com.udacity.asteroidradar.api.PictureOfTheDayService
import com.udacity.asteroidradar.database.NasaDao
//import com.udacity.asteroidradar.repository.PictureOfTheDayRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
//
//class AsteroidWorkerManager(context: Context, params: WorkerParameters) :
//    CoroutineWorker(context, params) {
//    override suspend fun doWork(): Result = coroutineScope {
//        try {
//            val job = async {
//                val nasaDao: NasaDao = NasaDatabase
//                    .getDatabase(applicationContext, this)
//                    .nasaDao()
//                val asteroidsRepository =
//                    AsteroidRepository(nasaDao, AsteroidFeedResponseModelItemService.instance)
//                asteroidsRepository.save()
//                // Log.i("Repository", "jdjd")
//                // val pictureOfTheDayRepository =
//                //     PictureOfTheDayRepository(nasaDao, PictureOfTheDayService.instance)
//                // pictureOfTheDayRepository.save()
//            }
//            job.await()
//            Result.success()
//        } catch (e: HttpException) {
//            Result.retry()
//        }
//    }
//}