package com.prince.studentconnect.data.local

import androidx.room.*

@Entity(tableName = "courses",
    foreignKeys = [
        ForeignKey(
            entity = Campus::class,
            parentColumns = ["campus_id"],
            childColumns = ["campus_id"],
            onDelete = ForeignKey.CASCADE
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
