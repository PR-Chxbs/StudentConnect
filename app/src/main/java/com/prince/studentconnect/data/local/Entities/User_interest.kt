package com.prince.studentconnect.data.local.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_interests",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = Interest::class,
            parentColumns = ["interest_id"],
            childColumns = ["interest_id"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["interest_id"])
    ]
)
data class User_interest(
    @PrimaryKey(autoGenerate = true)
    val user_interest_id: Int = 0,

    @ColumnInfo(name = "user_id")
    val user_id: String,

    @ColumnInfo(name = "interest_id")
    val interest_id: Int
)