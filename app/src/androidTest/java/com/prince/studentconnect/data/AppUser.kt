package com.prince.studentconnect.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class AppUser(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val email: String,
    val role: Role,
    val campusId: Long? = null
)

