package com.udacity.asteroidradar.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.AsteroidFeedResponseModelItemService
import com.udacity.asteroidradar.api.PictureOfTheDayService
import com.udacity.asteroidradar.database.NasaDao
import com.udacity.asteroidradar.database.NasaDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.PictureOfTheDayRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

class AsteroidWorkerManager(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            val job = async {
                val nasaDao: NasaDao = NasaDatabase
                    .getDatabase(applicationContext, this)
                    .nasaDao()
                val asteroidsRepository =
                    AsteroidRepository(nasaDao, AsteroidFeedResponseModelItemService.instance)
                asteroidsRepository.save()
                // Log.i("Repository", "jdjd")
                 val pictureOfTheDayRepository =
                     PictureOfTheDayRepository(nasaDao, PictureOfTheDayService.instance)
                 pictureOfTheDayRepository.save()
            }
            job.await()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}

//class EpisodeUpdateWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
//    // 1
//    override suspend fun doWork(): Result = coroutineScope {
//        // 2
//        val job = async {
//            // 3
//            val db = PodPlayDatabase.getInstance(applicationContext, this)
//            val repo = PodcastRepo(RssFeedService.instance, db.podcastDao())
//            // 4
//            val podcastUpdates = repo.updatePodcastEpisodes()
//            // 5
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                createNotificationChannel()
//            }
//            // 6
//            for (podcastUpdate in podcastUpdates) {
//                displayNotification(podcastUpdate)
//            }
//        }
//        // 7
//        job.await()
//        // 8
//        Result.success()
//    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel() {
//        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if (notificationManager.getNotificationChannel(EPISODE_CHANNEL_ID) == null) {
//            val channel = NotificationChannel(EPISODE_CHANNEL_ID, "Episodes", NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

//    private fun displayNotification(podcastInfo: PodcastRepo.PodcastUpdateInfo) {
//
//        val contentIntent = Intent(applicationContext, PodcastActivity::class.java)
//        contentIntent.putExtra(EXTRA_FEED_URL, podcastInfo.feedUrl)
//        val pendingContentIntent = PendingIntent.getActivity(applicationContext, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val notification = NotificationCompat.Builder(applicationContext, EPISODE_CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_episode_icon)
//            .setContentTitle(applicationContext.getString(R.string.episode_notification_title))
//            .setContentText(applicationContext.getString(R.string.episode_notification_text, podcastInfo.newCount, podcastInfo.name))
//            .setNumber(podcastInfo.newCount)
//            .setAutoCancel(true)
//            .setContentIntent(pendingContentIntent)
//            .build()
//
//        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(podcastInfo.name, 0, notification)
//    }
//
//    companion object {
//        const val EPISODE_CHANNEL_ID = "podplay_episodes_channel"
//        const val EXTRA_FEED_URL = "PodcastFeedUrl"
//    }
//}