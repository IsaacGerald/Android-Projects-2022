package com.douglasstarnes.basicworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWorker(context: Context, work: WorkerParameters): Worker(context, work) {

    override fun doWork(): Result {
        Thread.sleep(10000)
        val message: String? = inputData.getString("WORK_MESSAGE")
        WorkStatusSingleton.workComplete = true

        if (message != null){
            WorkStatusSingleton.workMessage = message
        }

        return Result.success()
    }
}