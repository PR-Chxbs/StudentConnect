package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Event_participation

@Dao
interface EventParticipationDao {
    @Query("SELECT * FROM event_participations")
    suspend fun getAllEventParticipations(): List<Event_participation>

    @Query("SELECT * FROM event_participations WHERE event_participation_id = :id")
    suspend fun getEventParticipationById(id: Int): Event_participation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventParticipation(eventParticipation: Event_participation)

    @Update
    suspend fun updateEventParticipation(eventParticipation: Event_participation)

    @Delete
    suspend fun deleteEventParticipation(eventParticipation: Event_participation)
}