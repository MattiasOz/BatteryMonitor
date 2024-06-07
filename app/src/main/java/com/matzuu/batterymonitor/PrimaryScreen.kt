package com.matzuu.batterymonitor

import android.os.BatteryManager
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.matzuu.batterymonitor.ui.components.BatteryMonitorBottomBar
import com.matzuu.batterymonitor.ui.screens.BigScreen

enum class CurrentScreen(@StringRes val title: Int) {
    BatteryLevels(title = R.string.battery_levels)
}

@Composable
fun PrimaryScreen(
    batteryManager: BatteryManager,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BatteryMonitorBottomBar(
                currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route,
                onFirstClick = {},
                onSecondClick = {},
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = rememberNavController(),
            startDestination = CurrentScreen.BatteryLevels.name,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable(route = CurrentScreen.BatteryLevels.name) {
                BigScreen(batteryManager = batteryManager)
            }
        }
    }
}