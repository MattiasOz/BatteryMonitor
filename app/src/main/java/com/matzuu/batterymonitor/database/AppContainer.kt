package com.matzuu.batterymonitor.database

import android.content.Context
import android.os.BatteryManager
import com.github.mikephil.charting.charts.LineChart

interface AppContainer {
    val batteryLevelRepository: BatteryLevelRepository
    val batteryManager: BatteryManager
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {

    override val batteryLevelRepository: BatteryLevelRepository by lazy {
        BatteryLevelRepositoryImpl(context, BatteryLevelDatabase.getDatabase(context).batteryLevelDao())
    }

    override val batteryManager: BatteryManager by lazy {
        context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    }
}
