package com.bizarr.myPlanApp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class Receivers() : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent, ) {
        val notificationManager = ContextCompat.getSystemService(
            context, NotificationManager::class.java
        ) as NotificationManager

        // Intent to start an Activity when the notification is clicked
        val activityIntent = Intent(context, MainActivity::class.java)
        activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            0,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val builder = NotificationCompat.Builder(context, "YOUR_CHANNEL_ID")
            .setSmallIcon(R.drawable.baseline_library_add_check_24)
            .setContentTitle("Don't forget!")
            .setContentText("Check if you have pending tasks")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(activityPendingIntent)  // Set the PendingIntent to the Notification Builder
            .setAutoCancel(true)  // Automatically remove the notification when it's tapped
        notificationManager.notify(1, builder.build())
    }
}