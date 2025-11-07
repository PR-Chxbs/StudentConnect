package com.prince.studentconnect.data.local.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.prince.studentconnect.data.local.Entities.User

@Entity(tableName = "module_teachers",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["lecturer_id"],
            childColumns = ["lecturer_id"],
            onDelete = ForeignKey.Companion.CASCADE
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