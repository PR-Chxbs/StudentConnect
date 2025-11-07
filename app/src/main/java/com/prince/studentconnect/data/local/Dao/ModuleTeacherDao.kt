package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Module_Teacher

@Dao
interface ModuleTeacherDao {
    @Query("SELECT * FROM module_teachers")
    suspend fun getAllModuleTeacher(): List<Module_Teacher>

    @Query("SELECT * FROM module_teachers WHERE module_teacher_id = :id")
    suspend fun getModuleTeacherById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModuleTeacher(moduleTeacher: Module_Teacher)

    @Update
    suspend fun updateModuleTeacher(moduleTeacher: Module_Teacher)

    @Delete
    suspend fun deleteModuleTeacher(moduleTeacher: Module_Teacher)
}