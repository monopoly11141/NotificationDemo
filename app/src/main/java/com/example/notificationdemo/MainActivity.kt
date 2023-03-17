package com.example.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import com.example.notificationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val channelID = "com.example.notificationdemo"
    private var notificationManager: NotificationManager? = null
    private val KEY_REPLY = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        val btnClick = binding.btnClick

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "DemoChannel", "This is a demo")

        btnClick.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {
        val tapResultIntent = Intent(this, SecondActivity::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, tapResultIntent, PendingIntent.FLAG_MUTABLE)

        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert your name here")
            build()
        }
        val replyAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "REPLY", pendingIntent).addRemoteInput(remoteInput)
                .build()


        val intent2 = Intent(this, DetailsActivity::class.java)
        val pendingIntent2: PendingIntent =
            PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_IMMUTABLE)
        val action2: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Details", pendingIntent2).build()

        val intent3 = Intent(this, SettingsActivity::class.java)
        val pendingIntent3: PendingIntent =
            PendingIntent.getActivity(this, 0, intent3, PendingIntent.FLAG_IMMUTABLE)
        val action3: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Settings", pendingIntent3).build()


        val notificationID = 45
        val notification = NotificationCompat.Builder(this@MainActivity, channelID)
            .setContentTitle("Demo title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(action2)
            .addAction(action3)
            .addAction(replyAction)
            .build()

        notificationManager?.notify(notificationID, notification)

    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)
        }
    }
}