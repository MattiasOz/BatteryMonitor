package com.matzuu.batterymonitor.ui.screens

import android.graphics.PointF
import android.os.BatteryManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.matzuu.batterymonitor.ui.views.BatteryChartView
import com.matzuu.batterymonitor.viewmodels.BatteryLevelsUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun BigScreen(
    batteryManager: BatteryManager,
    batteryLevelsUiState: BatteryLevelsUiState,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        val dateformatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeformatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        var batteryLevel by remember { mutableIntStateOf(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)) }
        var change by remember { mutableStateOf(false) }
        var time by remember { mutableStateOf(System.currentTimeMillis()) }
        Text("$batteryLevel")
        Text("Time: ${timeformatter.format(Date(time))}")
        Text("Date: ${dateformatter.format(Date(time))}")
        Button(onClick = {
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            time = System.currentTimeMillis()
            change = !change
        }) {
            Text("$change")
        }

        when (batteryLevelsUiState) {
            is BatteryLevelsUiState.Success -> {
                val points = batteryLevelsUiState.batteryLevels
                    .filter {
                        it.level != null
                    }
                    .map {
                        PointF(it.time.toFloat(), it.level!!/100f)
                    }
                AndroidView(
                    factory = { context ->
                        BatteryChartView(context, points)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            is BatteryLevelsUiState.Failure -> {

            }
        }
        val points = listOf(
            PointF(0.01f, 0.02f),
            PointF(0.03f, 0.04f),
            PointF(0.05f, 0.06f),
            PointF(1f, 1f)
        )

        AndroidView(
            factory = { context ->
                BatteryChartView(context, points)
            },
            modifier = Modifier.fillMaxSize()
        )
    }


//    BatteryChartView(LocalContext.current, listOf(PointF(1f, 2f)))

//    LineData(LineDataSet(listOf(Entry(1f, 2f)), "Label"))
}