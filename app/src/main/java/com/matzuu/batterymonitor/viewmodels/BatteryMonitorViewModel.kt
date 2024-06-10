package com.matzuu.batterymonitor.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.matzuu.batterymonitor.BatteryMonitorApplication
import com.matzuu.batterymonitor.database.BatteryLevelRepository
import com.matzuu.batterymonitor.models.BatteryLevel
import kotlinx.coroutines.launch

sealed interface BatteryLevelsUiState {
    data class Success(val batteryLevels: List<BatteryLevel>): BatteryLevelsUiState
    object Failure: BatteryLevelsUiState
}

class BatteryMonitorViewModel(
    private val batteryLevelRepository: BatteryLevelRepository
) : ViewModel() {

    var batteryLevelsUiState: BatteryLevelsUiState by mutableStateOf(BatteryLevelsUiState.Failure)
        private set

    fun getBatteryLevels() {
        viewModelScope.launch {
            batteryLevelsUiState = BatteryLevelsUiState.Failure
            val batteryLevels = batteryLevelRepository.getBatteryLevel()
            batteryLevelsUiState = BatteryLevelsUiState.Success(batteryLevels)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
//                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BatteryMonitorApplication)
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BatteryMonitorApplication
//                val application = this[APPLICATION_KEY] as BatteryMonitorApplication
                val batteryLevelRepository = application.container.batteryLevelRepository
                BatteryMonitorViewModel(
                    batteryLevelRepository = batteryLevelRepository
                )
            }
        }
    }
}