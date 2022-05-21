package com.douglasstarnes.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var i = 0
    private lateinit var btnCount: Button
    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCount = findViewById(R.id.btnCount)
        btnCount.text = "$i"

        btnCount.setOnClickListener {
            val job = scope.launch {
                for (j in 1..10) {
                    i += 1
                    btnCount.text = "$i"
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

        scope.cancel()
    }
}