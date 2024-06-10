package com.matzuu.batterymonitor

import android.app.Application
import com.matzuu.batterymonitor.database.AppContainer
import com.matzuu.batterymonitor.database.DefaultAppContainer

class BatteryMonitorApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}