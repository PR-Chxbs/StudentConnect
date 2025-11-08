package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Campus

@Dao
interface CampusDao {
    @Query(value = "SELECT * FROM campuses")
    suspend fun getAllCampuses(): List<Campus>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampuses(campus: List<Campus>)

    @Update
    suspend fun updateCampus(campus: Campus)

    @Delete
    suspend fun deleteModule(campus: Campus)
}