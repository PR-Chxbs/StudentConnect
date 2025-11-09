package com.prince.studentconnect.data.local.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campuses")
data class Campus(
    @PrimaryKey(autoGenerate = true)
    val campus_id: Int = 0,
    val name: String,
    val location: String,
    @ColumnInfo(name = "campus_image_ur")
    val campusImageUrl: String,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean
)