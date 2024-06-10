package com.matzuu.batterymonitor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matzuu.batterymonitor.models.BatteryLevel

@Dao
interface BatteryLevelDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(batteryLevel: BatteryLevel)

    @Query("SELECT * FROM battery_level ORDER BY time ASC")
    fun getAll(): List<BatteryLevel>

    @Query("DELETE FROM battery_level")
    fun clear()

}