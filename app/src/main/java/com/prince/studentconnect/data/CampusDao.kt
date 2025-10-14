package com.prince.studentconnect.data

import androidx.room.*

@Dao
interface CampusDao {
    @Query("SELECT * FROM campuses ORDER BY name")
    suspend fun getAll(): List<Campus>

    @Insert suspend fun insert(campus: Campus): Long
    @Update suspend fun update(campus: Campus)
    @Delete suspend fun delete(campus: Campus)
}
