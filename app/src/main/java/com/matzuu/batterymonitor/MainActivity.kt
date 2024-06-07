package com.matzuu.batterymonitor

import android.content.Context
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.matzuu.batterymonitor.ui.theme.BatteryMonitorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager

        setContent {
            BatteryMonitorTheme {
                PrimaryScreen(batteryManager = batteryManager)
            }
        }
    }
}
