package com.prince.studentconnect.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "module_teachers",
    ForeignKeys = [
        ForeignKey(
            entity = Lecturer::class,
            parentColumns= ["lecturer_id"],
            childColumns = ["lecturer_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Module_Teacher(
    @PrimaryKey(autoGenerate = true)
    val module_teacher_id: Int,
    val year_level: Int
)
