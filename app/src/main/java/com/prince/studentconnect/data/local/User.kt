package com.prince.studentconnect.data.local

import androidx.room.*

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
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["email"], unique = true), // makes email unique
        Index(value = ["campus_id"]),
        Index(value = ["course_id"])
    ]
)

data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: String,
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
    val bio: String,
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
        student,
        lecturer,
        admin
    }
}
