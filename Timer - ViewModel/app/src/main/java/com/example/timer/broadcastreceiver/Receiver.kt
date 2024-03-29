package com.example.timer.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.timer.MainActivity

class Receiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val messageText = intent?.getStringExtra("toast")

        Toast.makeText(context, messageText, Toast.LENGTH_SHORT).show()
    }
}