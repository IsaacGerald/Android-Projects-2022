package com.douglasstarnes.basicworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerRetry(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        return if (WorkStatusSingleton.workRetries < 3){
            println("Still working: ${WorkStatusSingleton.workRetries} at ${System.currentTimeMillis() / 1000}s")

            WorkStatusSingleton.workRetries++
            Result.retry()
        }else{
            Result.success()
        }
    }
}