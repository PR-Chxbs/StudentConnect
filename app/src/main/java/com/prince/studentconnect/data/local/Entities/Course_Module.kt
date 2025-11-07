package com.prince.studentconnect.data.local.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.prince.studentconnect.data.local.Entities.Module

@Entity(tableName = "course_modules",
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = Module::class,
            parentColumns = ["module_id"],
            childColumns = ["module_id"],
            onDelete = ForeignKey.Companion.CASCADE
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