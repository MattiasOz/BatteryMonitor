package com.matzuu.batterymonitor.viewmodels

import android.os.BatteryManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.matzuu.batterymonitor.BatteryMonitorApplication
import com.matzuu.batterymonitor.database.BatteryLevelRepository
import com.matzuu.batterymonitor.models.BatteryLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface BatteryLevelsUiState {
    data class Success(val batteryLevels: List<BatteryLevel>): BatteryLevelsUiState
    object Failure: BatteryLevelsUiState
}

class BatteryMonitorViewModel(
    private val batteryLevelRepository: BatteryLevelRepository,
    private val batteryManager: BatteryManager
) : ViewModel() {

    var batteryLevelsUiState: BatteryLevelsUiState by mutableStateOf(BatteryLevelsUiState.Failure)
        private set

    fun addBatteryLevel() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                batteryLevelRepository.insertBatteryLevel(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY))
            }
        }
    }

    fun scheduleWorker() {
        viewModelScope.launch {
            batteryLevelRepository.enqueueWorker()
        }
    }

    fun getBatteryLevels() {
        viewModelScope.launch {
            batteryLevelsUiState = BatteryLevelsUiState.Failure
            lateinit var batteryLevels: List<BatteryLevel>
            withContext(Dispatchers.IO) {
                batteryLevels = batteryLevelRepository.getBatteryLevel()
            }
            batteryLevelsUiState = BatteryLevelsUiState.Success(batteryLevels)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BatteryMonitorApplication
                val batteryLevelRepository = application.container.batteryLevelRepository
                val batteryManager = application.container.batteryManager
                BatteryMonitorViewModel(
                    batteryLevelRepository = batteryLevelRepository,
                    batteryManager = batteryManager
                )
            }
        }
    }
}