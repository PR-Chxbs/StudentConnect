package com.prince.studentconnect.data.local.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "modules",
    indices = [
        Index(value = ["name"], unique = true),
        Index(value = ["code"], unique = true)
    ])
data class Module(
    @PrimaryKey(autoGenerate = true)
    val module_id: Int,
    val name: String,
    val code: String,
    val description: String,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean
)