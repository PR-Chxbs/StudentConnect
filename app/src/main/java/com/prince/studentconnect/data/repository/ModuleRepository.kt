package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.ModuleApi
import com.prince.studentconnect.data.remote.dto.module.*
import com.prince.studentconnect.data.local.Dao.ModuleDao
import com.prince.studentconnect.data.local.Entities.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ModuleRepository(
    private val moduleApi: ModuleApi,
    private val moduleDao: ModuleDao
) {

    // fetch all modules
    suspend fun getModules(courseId: Int?, lecturerId: String?): List<Module> {
        return try {
            val response = moduleApi.getModules(courseId, lecturerId)
            if (response.isSuccessful) {
                val modulesFromApi = response.body()?.map { dto ->
                    Module(
                        module_id = dto.module_id,
                        name = dto.name,
                        code = dto.code,
                        description = dto.description,
                        isActive = dto.is_active //  match backend DTO field
                    )
                } ?: emptyList()

                // cache locally
                withContext(Dispatchers.IO) {
                    moduleDao.insertModules(modulesFromApi)
                }
                modulesFromApi
            } else {
                // fallback to local cache
                withContext(Dispatchers.IO) {
                    moduleDao.getAllModules()
                }
            }
        } catch (e: Exception) {
            // offline or network error fallback
            withContext(Dispatchers.IO) {
                moduleDao.getAllModules()
            }
        }
    }

    // get single module
    suspend fun getModule(moduleId: Int): Module? {
        return try {
            val response = moduleApi.getModule(moduleId)
            if (response.isSuccessful) {
                val dto = response.body()
                if (dto != null) {
                    val module = Module(
                        module_id = moduleId,
                        name = dto.name,
                        code = dto.code,
                        description = dto.description,
                        isActive = dto.is_active
                    )
                    withContext(Dispatchers.IO) {
                        moduleDao.updateModule(module)
                    }
                    module
                } else null
            } else {
                withContext(Dispatchers.IO) {
                    moduleDao.getAllModules().find { it.module_id == moduleId }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                moduleDao.getAllModules().find { it.module_id == moduleId }
            }
        }
    }

    // add new module
    suspend fun addModule(createModuleRequest: CreateModuleRequest): Response<CreateModuleResponse> {
        val response = moduleApi.addModule(createModuleRequest)
        if (response.isSuccessful) {
            response.body()?.let { moduleResponse ->
                val module = Module(
                    module_id = moduleResponse.module_id,
                    name = moduleResponse.name,
                    code = moduleResponse.code,
                    description = moduleResponse.description,
                    isActive = moduleResponse.is_active
                )
                withContext(Dispatchers.IO) {
                    moduleDao.insertModules(listOf(module))
                }
            }
        }
        return response
    }

    // update module
    suspend fun updateModule(
        updateModuleRequest: UpdateModuleRequest,
        moduleId: Int
    ): Response<UpdateModuleResponse> {
        val response = moduleApi.updateModule(updateModuleRequest, moduleId)
        if (response.isSuccessful) {
            response.body()?.let { moduleResponse ->
                val updatedModule = Module(
                    module_id = moduleResponse.module_id,
                    name = moduleResponse.name,
                    code = moduleResponse.code,
                    description = moduleResponse.description,
                    isActive = moduleResponse.is_active
                )
                withContext(Dispatchers.IO) {
                    moduleDao.updateModule(updatedModule)
                }
            }
        }
        return response
    }

    // delete module
    suspend fun deleteModule(moduleId: Int): Response<DeleteModuleResponse> {
        val response = moduleApi.deleteModule(moduleId)
        if (response.isSuccessful) {
            withContext(Dispatchers.IO) {
                moduleDao.getAllModules()
                    .find { it.module_id == moduleId }
                    ?.let { moduleDao.deleteModule(it) }
            }
        }
        return response
    }
}
