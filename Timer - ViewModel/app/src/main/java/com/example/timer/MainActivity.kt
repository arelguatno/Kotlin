package com.example.timer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import java.util.Date.from

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "1"

    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        initViewModels()
    }

    private fun initViewModels() {
        viewModel.getTime().observe(this, Observer {

            val textView = findViewById<TextView>(R.id.TextView)
            textView.text = it.toString()
            snackBar(textView, it)
        })

        viewModel.timerIsDone().observe(this, Observer {
            if (it == true) {
                sendLocalNotification()
                Toast.makeText(this, "Time's up", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun startTimer(view: View) {
        viewModel.startTimer()

    }

    private fun snackBar(textView: TextView, it: Int) {
        if (viewModel.getSnackBackTimer() == it) {
            val snack = Snackbar.make(
                textView,
                "Rotate the phone, and timer should persists",
                Snackbar.LENGTH_LONG
            )
            snack.show()
        }
    }

    private fun sendLocalNotification() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel =
                NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_DEFAULT)
            val manager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
            builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Title")
                .setContentText("Notification Text >=O")
                .setContentIntent(pendingIntent) // when you clicked the notification
                .setAutoCancel(true)
        } else {
            builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Title")
                .setContentText("Notification for <=O")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .priority = NotificationCompat.PRIORITY_DEFAULT
        }

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }

    }

}