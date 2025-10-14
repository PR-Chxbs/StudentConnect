package com.prince.studentconnect.data

import androidx.room.TypeConverter

class RoleConverter {
    @TypeConverter
    fun fromRole(role: Role): String = role.name

    @TypeConverter
    fun toRole(value: String): Role = Role.valueOf(value)
}
