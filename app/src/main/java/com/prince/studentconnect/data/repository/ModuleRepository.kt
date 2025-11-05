package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.ModuleApi
import com.prince.studentconnect.data.remote.dto.module.*
import retrofit2.Response

class ModuleRepository(
    private val moduleApi: ModuleApi
) {
    suspend fun getModules(courseId: Int, lecturerId: String): Response<List<GetModulesResponse>> {
        return moduleApi.getModules(courseId, lecturerId)
    }

    suspend fun getModule(moduleId: Int): Response<Unit> {
        return moduleApi.getModule(moduleId)
    }

    suspend fun addModule(createModuleRequest: CreateModuleRequest): Response<CreateModuleResponse> {
        return moduleApi.addModule(createModuleRequest)
    }

    suspend fun updateModule(updateModuleRequest: UpdateModuleRequest, moduleId: Int): Response<UpdateModuleResponse> {
        return moduleApi.updateModule(updateModuleRequest, moduleId)
    }

    suspend fun deleteModule(moduleId: Int): Response<DeleteModuleResponse> {
        return moduleApi.deleteModule(moduleId)
    }
}