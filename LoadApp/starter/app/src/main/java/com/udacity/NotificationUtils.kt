package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat

private val NOTIFICATION_ID = 0
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val image = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.download_notification
    )
    val style = NotificationCompat.BigPictureStyle()
        .bigPicture(image)
        .bigLargeIcon(null)
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.channel_id)
    )
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setStyle(style)
        .setLargeIcon(image)
        .addAction(
            NotificationCompat.Action(
                R.drawable.ic_launcher_background,
                applicationContext.getString(R.string.notification_button),
                contentPendingIntent
            )
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    this.notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    this.cancelAll()
}
