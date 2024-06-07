package com.matzuu.batterymonitor.ui.screens

import android.os.BatteryManager
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun BigScreen(
    batteryManager: BatteryManager
){
    Column {
        var batteryLevel by remember { mutableIntStateOf(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)) }
        var change by remember { mutableStateOf(false) }
        Text("$batteryLevel")
        Button(onClick = {
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            change = !change
        }) {
            Text("$change")
        }
    }
}