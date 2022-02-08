package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.udacity.Constants.Companion.GLIDE_URL
import com.udacity.Constants.Companion.RETROFIT_URL
import com.udacity.Constants.Companion.UDACITY_URL
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelNotifications()

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
            val checkedId =
                this.findViewById<RadioGroup>(R.id.download_radio_group).checkedRadioButtonId
            if (checkedId == -1) {
                Toast.makeText(
                    applicationContext, "Nothing selected",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                this.getURLWithRadioId(checkedId)?.let { URL -> download(URL) }
//               detailIntent.putExtra("downloadStatus", "downloading")
                custom_button.setState(ButtonState.Loading)
            }
        }

        createChannel(
            applicationContext.getString(R.string.channel_id),
            applicationContext.getString(R.string.channel_name)
        )
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            custom_button.setState(ButtonState.Completed)

            notificationManager.sendNotification(applicationContext.getString(R.string.notification_description), applicationContext)
        }
    }

    private fun download(URL: String) {

        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    companion object {
    }

    private fun getURLWithRadioId(radioId: Int): String? {
        return when (radioId) {
            R.id.udactity_radio -> {
                GLIDE_URL
            }
            R.id.glide_radio -> {
                UDACITY_URL
            }
            R.id.retrofit_radio -> {
                RETROFIT_URL
//                detailIntent.putExtra("repoName", "Retrofit")
            }
            else -> {
                null
            }
        }
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Completed Download"

//            val notificationManager = this.getSystemService(
//                NotificationManager::class.java
//            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}
