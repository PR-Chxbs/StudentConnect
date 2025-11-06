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
    @ColumnInfo(name = "course_module_id")
    val courseModuleId: Int = 0,
    @ColumnInfo(name = "year_level")
    val yearLevel: Int,
    @ColumnInfo(name = "course_id", index = true)
    val courseId: Int,
    @ColumnInfo(name = "module_id", index = true)
    val moduleId: Int
)
