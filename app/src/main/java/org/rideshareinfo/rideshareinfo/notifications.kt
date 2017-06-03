package org.rideshareinfo.rideshareinfo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.google.firebase.database.*

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

fun rideSurvayNotification(context: Context) {
    val notificationManager = context.getSystemService(NotificationManager::class.java)
    val notification = NotificationCompat.Builder(context)
            .setContentText("Did your ride go well?")
            .setSmallIcon(R.drawable.ic_notification)
            .addAction(0, "Yes", PendingIntent.getBroadcast(context, 0, RideCompleteReceiver.yes(context), 0))
            .addAction(0, "No", PendingIntent.getBroadcast(context, 0, RideCompleteReceiver.no(context), 0))
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
        val ref = FirebaseDatabase.getInstance().reference
                .child(KEY_RIDESHARE_DATA_BASE)
                .child(KEY_USERS_NODE)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.removeEventListener(this)
                val child = snapshot.children.iterator().next()
                val user = User.fromFirebase(child)
                FirebaseUser(when (intent.getStringExtra("status")) {
                    "yes" -> user.copy(status = "need a ride")
                    "no" -> user.copy(status = "don't need a ride")
                    else -> user
                }).updateUserStatus()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}

class RideCompleteReceiver : BroadcastReceiver() {
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
