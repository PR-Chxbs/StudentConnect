package com.prince.studentconnect.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campuses")
data class Campus(
    @PrimaryKey(autoGenerate = true)
    val campus_id: Int = 0,
    val name: String,
    val location: String,
    val campus_image_url: String,
    val is_active: Boolean
)
