package com.douglasstarnes.backoffcriteria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val workManager = WorkManager.getInstance(this)
    private lateinit var btnWorkerFail: Button
    private lateinit var btnWorkerRetry: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnWorkerFail = findViewById(R.id.btnWorkerFail)
        btnWorkerRetry = findViewById(R.id.btnWorkerRetry)

        btnWorkerFail.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<WorkerFail>().build()
            workManager.enqueue(workRequest)
        }

        btnWorkerRetry.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<WorkerRetry>()
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    10,
                    TimeUnit.SECONDS
                ).build()
            workManager.enqueue(workRequest)
        }
    }
}