package com.prince.studentconnect.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campuses")
data class Campus(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val location: String
)
