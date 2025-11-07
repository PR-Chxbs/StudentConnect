package com.prince.studentconnect.data.local.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.prince.studentconnect.data.converters.RoleConverter

@Entity(
    tableName = "users",
    foreignKeys = [
        ForeignKey(
            entity = Campus::class,
            parentColumns = ["campus_id"],
            childColumns = ["campus_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Course::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["email"], unique = true), // makes email unique
        Index(value = ["campus_id"]),
        Index(value = ["course_id"])
    ]
)

@TypeConverters(RoleConverter::class)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "middle_name")
    val middleName: String? = null,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val email: String,
    val phone: String? = null,
    @ColumnInfo(name = "student_number")
    val studentNumber: String? = null,
    val role: Role,
    val bio: String = "",
    @ColumnInfo(name = "campus_id", index = true)
    val campusId: Int,
    @ColumnInfo(name = "course_id", index = true)
    val courseId: Int? = null,
    @ColumnInfo(name = "profile_picture_url")
    val profilePictureUrl: String,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean
)
{
    enum class Role {
        student, lecturer, admin
    }
}