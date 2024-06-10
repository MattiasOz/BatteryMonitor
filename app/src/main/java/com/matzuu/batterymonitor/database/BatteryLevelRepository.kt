package com.matzuu.batterymonitor.database

import com.matzuu.batterymonitor.models.BatteryLevel

class BatteryLevelRepository(private val batteryLevelDao: BatteryLevelDao) {

    suspend fun insertBatteryLevel(batteryLevel: BatteryLevel) {
        batteryLevelDao.insert(batteryLevel)
    }

    suspend fun getBatteryLevel(): List<BatteryLevel> { // TODO: specify how many to fetch
        return batteryLevelDao.getAll()
    }

    suspend fun clearDatabase() {
        batteryLevelDao.clear()
    }
}