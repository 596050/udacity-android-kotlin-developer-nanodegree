package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.PictureOfTheDayService
import com.udacity.asteroidradar.database.NasaDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PictureOfTheDayRepository(
   private val nasaDao: NasaDao,
   private val service: PictureOfTheDayService
) {
   fun getAsteroidImageOfTheDayLiveData(): LiveData<PictureOfDay> = nasaDao.getPictureOfTheDay()

   suspend fun save() {
       GlobalScope.launch {
           val response = service.getPictureOfTheDayResponse()
           Log.i("response",response.toString())
           if (response != null) {
               nasaDao.createPictureOfTheDay(
                   PictureOfDay(
                       response.title, response.url, response.media_type
                   )
               )
           }
       }
   }
}