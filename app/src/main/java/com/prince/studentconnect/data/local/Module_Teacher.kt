package com.prince.studentconnect.data.local

import androidx.room.*

@Entity(tableName = "module_teachers",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns= ["lecturer_id"],
            childColumns = ["lecturer_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
     indices = [
         Index(value = ["lecturer_id"])
     ])

data class Module_Teacher(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("module_teacher_id")
    val moduleTeacherId: Int,
    @ColumnInfo(name = "year_level")
    val yearLevel: Int,
    @ColumnInfo(name = "lecturer_id")
    val lecturerId: Int
)
