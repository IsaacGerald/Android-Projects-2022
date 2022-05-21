package com.example.powerreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val intentAction = intent.action

        if (intentAction != null){
            var toastMessage = "Unknown intent action"
            when(intentAction){
                Intent.ACTION_POWER_CONNECTED -> {
                    toastMessage = "Power Connected"
                }
                Intent.ACTION_POWER_DISCONNECTED -> {
                    toastMessage = "Power disconnected"
                }

                Intent.ACTION_HEADSET_PLUG -> {
                    when(intent.getIntExtra("state", -1)){
                        0 -> {
                            toastMessage = "Headset unplugged"
                        }
                        1 -> {
                            toastMessage = "Headset plugged"
                            
                        }
                    }
                }
                ACTION_CUSTOM_BROADCAST -> {
                    toastMessage = "Custom Broadcast Received"
                }
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
        }
    }
}