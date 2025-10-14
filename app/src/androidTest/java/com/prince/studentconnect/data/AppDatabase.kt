package com.prince.studentconnect.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Campus::class, AppUser::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoleConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun campusDao(): CampusDao
    abstract fun userDao(): UserDao
}
