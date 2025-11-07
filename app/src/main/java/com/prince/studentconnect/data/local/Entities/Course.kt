package com.prince.studentconnect.data.local.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "courses",
    foreignKeys = [
        ForeignKey(
            entity = Campus::class,
            parentColumns = ["campus_id"],
            childColumns = ["campus_id"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [
        Index(value = ["campus_id"])
    ]
    )
data class Course(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    val courseId: Int = 0,
    val name: String,
    val description: String,
    @ColumnInfo(name = "duration_years")
    val durationYears: Int,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean,
    @ColumnInfo(name = "campus_id", index = true)
    val campusId: String
)