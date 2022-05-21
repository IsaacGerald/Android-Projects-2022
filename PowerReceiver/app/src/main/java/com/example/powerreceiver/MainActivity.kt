package com.example.powerreceiver

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.powerreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mReceiver: CustomReceiver
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mReceiver = CustomReceiver()
        val filter = IntentFilter()

        //Intent filters specify the types of intents a component can receive
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        filter.addAction(Intent.ACTION_HEADSET_PLUG)

        //register receiver
        this.registerReceiver(mReceiver, filter)

        //register local custom broadcast receiver
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mReceiver, IntentFilter(ACTION_CUSTOM_BROADCAST))

        binding.sendBroadcast.setOnClickListener {
            sendCustomBroadcast()
        }


    }

    private fun sendCustomBroadcast() {
        //Sending a custom broadcast (Local)
        val intent = Intent(ACTION_CUSTOM_BROADCAST)

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onDestroy() {
        //Unregister the receiver
        this.unregisterReceiver(mReceiver)
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mReceiver)
        super.onDestroy()
    }


}