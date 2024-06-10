package com.matzuu.batterymonitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.matzuu.batterymonitor.models.BatteryLevel


@Database(entities = [BatteryLevel::class], version = 1)
abstract class BatteryLevelDatabase : RoomDatabase() {

    abstract fun batteryLevelDao(): BatteryLevelDao

    companion object {
        @Volatile
        private var Instance: BatteryLevelDatabase? = null

        fun getDatabase(ctx: Context): BatteryLevelDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(ctx, BatteryLevelDatabase::class.java, "battery_level_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}