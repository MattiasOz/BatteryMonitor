package com.matzuu.batterymonitor.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.matzuu.batterymonitor.utils.dateFormatter
import com.matzuu.batterymonitor.utils.timeFormatter
import com.matzuu.batterymonitor.viewmodels.BatteryLevelsUiState


@Composable
fun BatteryListScreen(
    batteryLevelsUiState: BatteryLevelsUiState,
    onClickFunction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when(batteryLevelsUiState) {
        is BatteryLevelsUiState.Success -> {
            LazyColumn(
                modifier = modifier
            ) {
                var odd = true
                items(batteryLevelsUiState.batteryLevels) { (time, level) ->
                    BatteryRow(
                        time,
                        level,
                        modifier = Modifier
                            .background(if (odd) Color.DarkGray else Color.Gray)
                    )
                    odd = !odd
                }
            }
        }
        BatteryLevelsUiState.Failure -> TODO()
    }

}


@Composable
fun BatteryRow(
    time: Long,
    batteryLevel: Int?,
    modifier: Modifier = Modifier
) {
    val color = generateColor(batteryLevel)
    Row(
        modifier = modifier
            .background(color)
    ) {
        Text(
            text = "${dateFormatter(time)} ${timeFormatter(time)}",
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "$batteryLevel%",
            modifier = Modifier.weight(1f)
        )
    }
}

private fun generateColor(batteryLevel: Int?): Color {
    if (batteryLevel == null) {
        return Color(0, 0, 0xff)
    }
    val green = (batteryLevel.div(100f) * 0xff).toInt()
    val red = 0xff - green
    return Color(red, green, 0)
}