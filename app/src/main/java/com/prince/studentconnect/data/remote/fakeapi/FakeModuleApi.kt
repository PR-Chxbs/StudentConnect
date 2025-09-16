package com.prince.studentconnect.data.remote.fakeapi

import com.prince.studentconnect.data.remote.api.ModuleApi
import com.prince.studentconnect.data.remote.dto.module.*
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class FakeModuleApi : ModuleApi {

    private val modules = mutableListOf<Module>()
    private var nextId = 1

    override suspend fun getModules(courseId: Int?, lecturerId: String?): Response<GetModulesResponse> {
        // Filtering by courseId or lecturerId is skipped for fake
        return Response.success(GetModulesResponse(modules = modules.toTypedArray()))
    }

    override suspend fun getModule(moduleId: Int): Response<Unit> {
        val exists = modules.any { it.module_id == moduleId }
        return if (exists) Response.success(Unit)
        else Response.error(404, """{"error":"Module not found"}""".toResponseBody("application/json".toMediaType()))
    }

    override suspend fun addModule(request: CreateModuleRequest): Response<Unit> {
        val newModule = Module(
            module_id = nextId++,
            name = request.name,
            code = request.code
        )
        modules.add(newModule)
        return Response.success(Unit)
    }

    override suspend fun updateModule(request: UpdateModuleRequest, moduleId: Int): Response<Unit> {
        val index = modules.indexOfFirst { it.module_id == moduleId }
        return if (index != -1) {
            val old = modules[index]
            modules[index] = old.copy(
                name = request.name,
                code = request.code
            )
            Response.success(Unit)
        } else {
            Response.error(404, """{"error":"Module not found"}""".toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun deleteModule(moduleId: Int): Response<Unit> {
        val removed = modules.removeIf { it.module_id == moduleId }
        return if (removed) Response.success(Unit)
        else Response.error(404, """{"error":"Module not found"}""".toResponseBody("application/json".toMediaType()))
    }
}
