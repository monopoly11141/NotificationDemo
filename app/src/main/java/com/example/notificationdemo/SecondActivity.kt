package com.example.notificationdemo

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        receiveInput()
    }

    private fun receiveInput() {
        val KEY_REPLY = "key_reply"


        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        remoteInput?.let {
            val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
            val tvSecond = findViewById<TextView>(R.id.tvSecond)

            tvSecond.text = inputString

            val channelID = "com.example.notificationdemo"
            val notificationID = 45

            val repliedNotification = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("Your reply was received")
                .build()

            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationID, repliedNotification)
        }



    }
}