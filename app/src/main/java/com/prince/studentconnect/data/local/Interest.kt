package com.prince.studentconnect.data.local

import androidx.room.*

@Entity(tableName = "interests")
data class Interest(
    @PrimaryKey(autoGenerate = true)
    val interest_id: Int = 0,
    val emoji: String,
    val text: String,
    @ColumnInfo(name = "color_code")
    val colorCode: String
)
