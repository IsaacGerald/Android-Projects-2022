package com.douglasstarnes.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var i = 0
    private lateinit var btnCounter: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCounter = findViewById(R.id.btnCounter)
        btnCounter.text = "$i"

        btnCounter.setOnClickListener {
            val job = lifecycleScope.launch {
                for (v in 1..10) {
                    i += 1
                    btnCounter.text = "$i"
                    Log.d("DEMO", "$i")
                    delay(1000)
                }
            }
            job.invokeOnCompletion {
                Log.d("DEMO", "Coroutine finished")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}