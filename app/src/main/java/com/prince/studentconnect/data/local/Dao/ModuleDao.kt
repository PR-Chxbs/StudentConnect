package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Module

@Dao
interface ModuleDao {
    @Query("SELECT * FROM modules")
    suspend fun getAllModules(): List<Module>

    @Query("SELECT * FROM modules WHERE module_id = :id")
    suspend fun getModuleById(id: Int): Module

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModule(module: Module)

    @Delete
    suspend fun deleteModule(module: Module)

    @Update
    suspend fun updateModule(module: Module)
}