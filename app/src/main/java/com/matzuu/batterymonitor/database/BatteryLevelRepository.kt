package com.matzuu.batterymonitor.database

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.matzuu.batterymonitor.models.BatteryLevel
import com.matzuu.batterymonitor.utils.BATTERY_COLLECT_WORK_REQUEST_TAG
import com.matzuu.batterymonitor.workers.BatteryLevelCollectWorker
import java.util.concurrent.TimeUnit


interface BatteryLevelRepository {
    suspend fun insertBatteryLevel(currentLevel: Int)

    suspend fun getBatteryLevel(): List<BatteryLevel>

    suspend fun clearDatabase()

    fun enqueueWorker()
}

class BatteryLevelRepositoryImpl(
    ctx: Context,
    private val batteryLevelDao: BatteryLevelDao
) : BatteryLevelRepository {
    private val workManager = WorkManager.getInstance(ctx)

    override suspend fun insertBatteryLevel(currentLevel: Int) {
        batteryLevelDao.insert(BatteryLevel(level = currentLevel))
    }

    override suspend fun getBatteryLevel(): List<BatteryLevel> { // TODO: specify how many to fetch
        return batteryLevelDao.getAll()
    }

    override suspend fun clearDatabase() {
        batteryLevelDao.clear()
    }

    override fun enqueueWorker() {
        val constraints = Constraints.Builder()
            .build()

        val workRequest = PeriodicWorkRequestBuilder<BatteryLevelCollectWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(BATTERY_COLLECT_WORK_REQUEST_TAG, ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, workRequest)
    }
}