package com.prince.studentconnect.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_modules",
    foreignKeys = [
        foreignKey(
            entity = Course::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        ),
        foreignKey (
            entity = Module::class,
            parentColumns = ["module_id"],
            childColumns = ["module_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Course_Module(
    val module_id: Int = 0,
    val year_level: Int
)
