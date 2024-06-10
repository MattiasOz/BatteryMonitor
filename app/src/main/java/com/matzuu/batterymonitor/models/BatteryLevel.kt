package com.matzuu.batterymonitor.models

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "battery_level", primaryKeys = ["time"])
data class BatteryLevel (
    @SerialName("time")
    var time: Long = System.currentTimeMillis(),

    @SerialName("level")
    var level: Int? = null,
)