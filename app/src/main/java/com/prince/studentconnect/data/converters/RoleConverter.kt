package com.prince.studentconnect.data.converters

import androidx.room.TypeConverter
import com.prince.studentconnect.data.local.Entities.*

class RoleConverter {
    @TypeConverter
    fun fromRole(role: User.Role): String = role.name

    @TypeConverter
    fun toRole(value: String): User.Role = User.Role.valueOf(value)
}