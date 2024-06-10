package com.matzuu.batterymonitor.ui.screens

import android.graphics.PointF
import android.os.BatteryManager
import android.util.Log
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

private const val TAG = "BigScreen"

@Composable
fun BigScreen(
    batteryManager: BatteryManager,
    batteryLevelsUiState: BatteryLevelsUiState,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
//        val dateformatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        val timeformatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
//        var batteryLevel by remember { mutableIntStateOf(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)) }
//        var change by remember { mutableStateOf(false) }
//        var time by remember { mutableStateOf(System.currentTimeMillis()) }
//        Text("$batteryLevel")
//        Text("Time: ${timeformatter.format(Date(time))}")
//        Text("Date: ${dateformatter.format(Date(time))}")
//        Button(onClick = {
//            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
//            time = System.currentTimeMillis()
//            change = !change
//        }) {
//            Text("$change")
//        }

        when (batteryLevelsUiState) {
            is BatteryLevelsUiState.Success -> {
                val first: Long
                val last: Long
                Log.d(TAG, "BatteryLevels: $batteryLevelsUiState.batteryLevels")
                var points: List<PointF> = listOf()
                try {
                    points = batteryLevelsUiState.batteryLevels
                        .filter {
                            it.level != null
                        }
                        .apply {
                            first = first().time
                            last = last().time
                        }
                        .map {
                            val time = (it.time - first) / last
                            PointF(time.toFloat(), it.level!!/100f)
                        }

                } catch (e: NoSuchElementException) {
                    Text("No data")
                }
                AndroidView(
                    factory = { context ->
                        BatteryChartView(context, points)
                    },
                    modifier = Modifier.fillMaxSize()
                )

            }
            is BatteryLevelsUiState.Failure -> {
                Text("UiState failure")
            }
        }
    }


//    BatteryChartView(LocalContext.current, listOf(PointF(1f, 2f)))

//    LineData(LineDataSet(listOf(Entry(1f, 2f)), "Label"))
}