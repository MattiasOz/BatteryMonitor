package com.matzuu.batterymonitor.ui.components

import android.util.Log
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
    navigationStandard: () -> Unit,
    navigateList: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        Log.d("BottomBar", "BatteryMonitorBottomBar: $currentRoute")
        NavigationBarItem(
            selected = currentRoute == CurrentScreen.BatteryLevels.name,
            onClick = navigationStandard,
            label = {
                Text("Standard")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = currentRoute == CurrentScreen.ListScreen.name,
            onClick = navigateList,
            label = {
                    Text("List")
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