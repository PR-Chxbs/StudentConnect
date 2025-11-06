package com.prince.studentconnect.data.local

import androidx.room.*

@Entity(tableName = "course_modules",
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Module::class,
            parentColumns = ["module_id"],
            childColumns = ["module_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["course_id"]),
        Index(value = ["module_id"])
    ]
)
data class Course_Module(
    @PrimaryKey(autoGenerate = true)
    val course_module_id: Int = 0,
    val year_level: Int,
    @ColumnInfo(name = "course_id")
    val course_id: Int,

    @ColumnInfo(name = "module_id")
    val module_id: Int
)
