package com.prince.studentconnect.data.local

import androidx.room.*
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "conversations",
    foreignKeys = [
        ForeignKey(
            entity = Module::class,
            parentColumns = ["module_id"],
            childColumns = ["module_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["module_id"])]
)
data class Conversation(
    @PrimaryKey(autoGenerate = true)
    val conversation_id: Int = 0,
    val name: String? = null,
    val max_members: Int,
    val type: Type,
    val visibility: Visibility,
    val member_count: Int,
    val date_created: LocalDateTime,
    @ColumnInfo(index = true)
    val module_id: Int
)

enum class Type {
    private, group, module_default
}

enum class Visibility {
    public, private, restricted
}
