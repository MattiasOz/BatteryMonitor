package com.matzuu.batterymonitor

import android.os.BatteryManager
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.matzuu.batterymonitor.ui.components.BatteryMonitorBottomBar
import com.matzuu.batterymonitor.ui.screens.BigScreen
import com.matzuu.batterymonitor.viewmodels.BatteryMonitorViewModel
import com.matzuu.batterymonitor.workers.BatteryLevelCollectWorker

enum class CurrentScreen(@StringRes val title: Int) {
    BatteryLevels(title = R.string.battery_levels)
}

@Composable
fun PrimaryScreen(
    batteryManager: BatteryManager,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val batteryMonitorViewModel: BatteryMonitorViewModel = viewModel(factory = BatteryMonitorViewModel.Factory)
    BatteryLevelCollectWorker.viewmodel = batteryMonitorViewModel
    BatteryLevelCollectWorker.batteryManager = batteryManager
    batteryMonitorViewModel.scheduleWorker()
    batteryMonitorViewModel.getBatteryLevels()

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
                BigScreen(
                    batteryManager = batteryManager,
                    batteryLevelsUiState = batteryMonitorViewModel.batteryLevelsUiState
                )
            }
        }
    }
}