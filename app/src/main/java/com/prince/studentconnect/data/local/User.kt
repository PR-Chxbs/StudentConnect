package com.prince.studentconnect.data.entities

import androidx.room.*
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
            onDelete = ForeignKey.SET_NULL
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
    @PrimaryKey val user_id: String,
    val first_name: String,
    val middle_name: String? = null,
    val last_name: String,
    val email: String,
    val phone: String? = null,
    val student_number: String? = null,
    val role: Role,
    val bio: String,
    val campus_id: Int,
    val course_id: Int? = null,
    val profile_picture_url: String,
    val is_active: Boolean
) {
    enum class Role {
        student,
        lecturer,
        admin
    }
}
