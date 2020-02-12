package com.nightowl.fcm_test

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var msg: String? = null
    private var title: String? = null

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        title = p0.notification?.title
        msg = p0.notification?.body

        var intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val contentIntent = PendingIntent.getActivity(this, 0 , intent, 0)

        val mBuilder =
            NotificationCompat.Builder(this, "MyChannelID").setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(1, 1000))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, mBuilder.build())

        mBuilder.setContentIntent(contentIntent)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("ljw", "Refreshed token: $p0")
    }
}