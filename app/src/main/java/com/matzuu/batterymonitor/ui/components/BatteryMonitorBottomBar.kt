package com.matzuu.batterymonitor.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.matzuu.batterymonitor.CurrentScreen

@Composable
fun BatteryMonitorBottomBar(
    currentRoute: String?,
    onFirstClick: () -> Unit,
    onSecondClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            selected = currentRoute == CurrentScreen.BatteryLevels.name,
            onClick = onFirstClick,
            label = {
                Text("First")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = onSecondClick,
            label = {
                    Text("Second")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null
                )
            }
        )
    }
}