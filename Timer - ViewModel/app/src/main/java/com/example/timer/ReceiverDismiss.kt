package com.example.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class ReceiverDismiss : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        with(context?.let { NotificationManagerCompat.from(it) }) {
//            this?.cancel(1)
//        }

        context?.let {
            val notificationManagerCompat = NotificationManagerCompat.from(it).apply {
                cancel(1)
            }
          //  notificationManagerCompat.cancel(1)
        }
    }
}