package com.yasserakbbach.tracker_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yasserakbbach.tracker_data.local.entity.TrackedFoodEntity

@Database(
    version = 1,
    entities = [TrackedFoodEntity::class],
)
abstract class TrackerDatabase : RoomDatabase() {

    abstract val trackerDao: TrackerDao
}