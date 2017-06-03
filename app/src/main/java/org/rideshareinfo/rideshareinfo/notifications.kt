package org.rideshareinfo.rideshareinfo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat

fun askUserStatusNotification(context: Context) {
    val notificationManager = context.getSystemService(NotificationManager::class.java)
    val notification = NotificationCompat.Builder(context)
            .setContentText("Do you need a ride?")
            .setSmallIcon(R.drawable.ic_notification)
            .addAction(0, "Yes", PendingIntent.getBroadcast(context, 0, RideStatusReceiver.yes(context), 0))
            .addAction(0, "No", PendingIntent.getBroadcast(context, 0, RideStatusReceiver.no(context), 0))
            .build()
    notificationManager.notify(0, notification)
}

class RideStatusReceiver : BroadcastReceiver() {
    companion object {
        fun yes(context: Context): Intent = Intent(context, RideStatusReceiver::class.java)
                .putExtra("status", "yes")

        fun no(context: Context): Intent = Intent(context, RideStatusReceiver::class.java)
                .putExtra("status", "no")
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.cancelAll()
        when (intent.getStringExtra("status")) {
            "yes" -> {
            }
            "no" -> {
            }
        }
    }
}