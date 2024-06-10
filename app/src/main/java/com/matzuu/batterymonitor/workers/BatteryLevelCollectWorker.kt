package com.matzuu.batterymonitor.workers

import android.content.Context
import android.os.BatteryManager
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.matzuu.batterymonitor.viewmodels.BatteryMonitorViewModel

private const val TAG = "BatteryLevelCollectWorker"

class BatteryLevelCollectWorker(
    private val ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        try {
            viewmodel!!.insertBatteryLevel(batteryManager!!.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY))
        } catch (e: NullPointerException) {
            Log.e(TAG, "viewmodel or batteryManager is null")
            return Result.failure()
        }
        return Result.success()
    }


    companion object {
        var viewmodel: BatteryMonitorViewModel? = null
        var batteryManager: BatteryManager? = null
    }
}



