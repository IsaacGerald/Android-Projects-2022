package com.douglasstarnes.basicworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val workManager = WorkManager.getInstance(this)
    lateinit var btnStartWork: Button
    lateinit var btnWorkStatus: Button
    lateinit var btnResetStatus: Button
    lateinit var btnWorkUIThread: Button
    lateinit var btnWorkerFail: Button
    lateinit var btnWorkerRetry: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartWork = findViewById(R.id.btnStartWork)
        btnWorkStatus = findViewById(R.id.btnWorkStatus)
        btnResetStatus = findViewById(R.id.btnResetStatus)
        btnWorkUIThread = findViewById(R.id.btnWorkUIThread)
        btnWorkerFail = findViewById(R.id.btnWorkerFail)
        btnWorkerRetry = findViewById(R.id.btnWorkerRetry)

        btnStartWork.setOnClickListener {

//            val data = Data.Builder()
//                .putString("WORK_MESSAGE", "Work Completed!")
//                .build()
            //Alternatively you can use kotlin function
            val data = workDataOf("WORK_MESSAGE" to "Work Completed!")

            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

//            val workRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
            val workRequest =
                OneTimeWorkRequestBuilder<SimpleWorker>()
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build() // OneTime work request


            val periodicWorkRequest = PeriodicWorkRequestBuilder<SimpleWorker>(
                5, TimeUnit.MINUTES,
                1, TimeUnit.MINUTES //Time window
            ).build()// periodic work request

            workManager.enqueue(workRequest)
        }

        btnWorkStatus.setOnClickListener {
            val toast = Toast.makeText(
                this,
                "The work status is: ${WorkStatusSingleton.workMessage}",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }

        btnResetStatus.setOnClickListener {
            WorkStatusSingleton.workComplete = false
        }

        btnWorkUIThread.setOnClickListener {
            Thread.sleep(10000)
            WorkStatusSingleton.workComplete = true
        }

        btnWorkerFail.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<WorkerFail>()
                .build()
            workManager.enqueue(workRequest)
        }

        btnWorkerRetry.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<WorkerRetry>()
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
                .build()

            workManager.enqueue(workRequest)
        }
    }
}