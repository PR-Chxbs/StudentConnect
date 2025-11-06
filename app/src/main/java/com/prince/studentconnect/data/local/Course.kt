package com.prince.studentconnect.data.local

import androidx.room.*

@Entity(tableName = "courses",
    foreignKeys = [
        foreignKey(
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
    val course_id: Int = 0,
    val name: String,
    val description: String,
    val duration_years: Int,
    val is_active: Boolean,
    @ColumnInfo(name = "campus_id")
    val campus_id: String
)
