package com.matzuu.batterymonitor.database

import android.content.Context
import com.github.mikephil.charting.charts.LineChart

interface AppContainer {
    val batteryLevelRepository: BatteryLevelRepository
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {
    override val batteryLevelRepository: BatteryLevelRepository by lazy {
        BatteryLevelRepositoryImpl(context, BatteryLevelDatabase.getDatabase(context).batteryLevelDao())    }
}
