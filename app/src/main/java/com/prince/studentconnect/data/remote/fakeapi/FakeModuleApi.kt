package com.prince.studentconnect.data.remote.fakeapi

import com.prince.studentconnect.data.remote.api.ModuleApi
import com.prince.studentconnect.data.remote.dto.module.*
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class FakeModuleApi : ModuleApi {

    private val modules = mutableListOf<Module>()
    private var nextId = 1

    override suspend fun getModules(courseId: Int?, lecturerId: String?): Response<List<GetModulesResponse>> {
        val modules = mutableListOf<GetModulesResponse>()

        modules.forEach { module ->
            modules.add(
                GetModulesResponse(
                    module_id = module.module_id,
                    name = module.name,
                    code = module.code,
                    description = module.description
                )
            )
        }
        // Filtering by courseId or lecturerId is skipped for fake
        return Response.success(modules)
    }

    override suspend fun getModule(moduleId: Int): Response<Unit> {
        val exists = modules.any { it.module_id == moduleId }
        return if (exists) Response.success(Unit)
        else Response.error(404, """{"error":"Module not found"}""".toResponseBody("application/json".toMediaType()))
    }

    override suspend fun addModule(request: CreateModuleRequest): Response<CreateModuleResponse> {
        val newModule = Module(
            module_id = nextId++,
            name = request.name,
            code = request.code
        )
        modules.add(newModule)
        return Response.success(CreateModuleResponse(
            success = true,
            module = CreateModuleResponseModule(
                module_id = newModule.module_id,
                name = newModule.name,
                code = newModule.code,
                description = "Test module",
                is_active = true
            )
        ))
    }

    override suspend fun updateModule(request: UpdateModuleRequest, moduleId: Int): Response<UpdateModuleResponse> {
        val index = modules.indexOfFirst { it.module_id == moduleId }
        return if (index != -1) {
            val old = modules[index]
            modules[index] = old.copy(
                name = request.name,
                code = request.code
            )
            Response.success(UpdateModuleResponse(
                success = true,
                updated_at = "Now"
            ))
        } else {
            Response.error(404, """{"error":"Module not found"}""".toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun deleteModule(moduleId: Int): Response<DeleteModuleResponse> {
        val removed = modules.removeIf { it.module_id == moduleId }
        return if (removed) Response.success(DeleteModuleResponse(
            success = true,
            message = "Successfully deactivated module",
            deleted_at = "Now"
        ))
        else Response.error(404, """{"error":"Module not found"}""".toResponseBody("application/json".toMediaType()))
    }
}
