package com.Sobol.vkplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.R
import android.app.Notification
import android.app.PendingIntent
import com.Sobol.vkplayer.ui.MainActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.content.Context.NOTIFICATION_SERVICE
import android.support.v4.app.NotificationCompat


class PlayerService : Service() {

    val mediaPlayer = MediaPlayer()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        var notifManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = "12345qwerty"
        val channelTitle = "qwerty"
        val intent: Intent
        val pendingIntent: PendingIntent
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            var mChannel: NotificationChannel? = notifManager.getNotificationChannel(id)
            if (mChannel == null) {
                mChannel = NotificationChannel(id, channelTitle, importance)
                notifManager.createNotificationChannel(mChannel)
            }
            builder = NotificationCompat.Builder(this, id)
            intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            builder.setContentTitle("text")
                .setSmallIcon(R.drawable.presence_audio_online)
                .setContentText("text")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker("text")
        } else {
            builder = NotificationCompat.Builder(this, id)
            intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            builder.setContentTitle("text")
                .setSmallIcon(R.drawable.presence_audio_online)
                .setContentText("this")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker("this")
        }
        val notification = builder.build()

        startForeground(555, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val uri = intent!!.getParcelableExtra<Uri>("uri")
        Log.d("uri2", uri.toString())
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this, uri)
        mediaPlayer.prepare()
        mediaPlayer.start()
        return super.onStartCommand(intent, flags, startId)
    }

}