package com.prince.studentconnect.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface PendingEventDao {
    @Insert
    suspend fun insert(event: PendingEvent): Long

    @Query("SELECT * FROM pending_events ORDER BY createdAtMillis ASC")
    suspend fun getAll(): List<PendingEvent>

    @Query("DELETE FROM pending_events WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM pending_events")
    suspend fun clearAll()
}