package com.prince.studentconnect.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Module(
    @PrimaryKey(autoGenerate = true)
    val module_id: Int,
    val name: String? unique,
    val code: String unique,
    val description: String,
    val is_active: Boolean
)
