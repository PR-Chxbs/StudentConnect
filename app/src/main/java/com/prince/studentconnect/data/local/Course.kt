package com.prince.studentconnect.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses",
    foreignKeys = [
        foreignKey(
            entity = Campus::class,
            parentColumns = ["campus_id"],
            childColumns = ["campus_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
    )
data class Course(
    @PrimaryKey(autoGenerate = true)
    val course_id: Int = 0,
    val name: String,
    val description: String,
    val duration_years: Int,
    val is_active: Boolean
)
