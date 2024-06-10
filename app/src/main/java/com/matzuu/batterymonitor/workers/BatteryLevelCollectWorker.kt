package com.matzuu.batterymonitor.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters


class BatteryLevelCollectWorker(
    private val ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}



