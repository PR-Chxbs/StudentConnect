package com.prince.studentconnect.data.remote.fakeapi

import com.prince.studentconnect.data.remote.api.CampusApi
import com.prince.studentconnect.data.remote.dto.campus.AddCampusRequest
import com.prince.studentconnect.data.remote.dto.campus.Campus
import com.prince.studentconnect.data.remote.dto.campus.GetCampusesResponse
import com.prince.studentconnect.data.remote.dto.campus.UpdateCampusRequest
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class FakeCampusApi : CampusApi {

    // In-memory storage for campuses
    private val campuses = mutableListOf(
        Campus(campus_id = 1, name = "Main Campus", location = "Pretoria", campus_image_url = "https://example.com/main.png"),
        Campus(campus_id = 2, name = "City Campus", location = "Johannesburg", campus_image_url = "https://example.com/city.png"),
        Campus(campus_id = 3, name = "Coastal Campus", location = "Durban", campus_image_url = "https://example.com/coastal.png")
    )
    private var nextId = 4

    override suspend fun getCampuses(): Response<GetCampusesResponse> {
        return Response.success(GetCampusesResponse(campus = campuses.toTypedArray()))
    }

    override suspend fun addCampus(request: AddCampusRequest): Response<Unit> {
        val newCampus = Campus(
            campus_id = nextId++,
            name = request.name,
            location = request.location,
            campus_image_url = request.campus_image_url
        )
        campuses.add(newCampus)
        return Response.success(Unit)
    }

    override suspend fun updateCampus(request: UpdateCampusRequest, campusId: Int): Response<Unit> {
        val campusIndex = campuses.indexOfFirst { it.campus_id == campusId }
        return if (campusIndex != -1) {
            val oldCampus = campuses[campusIndex]
            campuses[campusIndex] = oldCampus.copy(
                name = request.name,
                location = request.location,
                campus_image_url = request.campus_image_url
            )
            Response.success(Unit)
        } else {
            val errorJson = """{"error":"Campus not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun deleteCampus(campusId: Int): Response<Unit> {
        val removed = campuses.removeIf { it.campus_id == campusId }
        return if (removed) {
            Response.success(Unit)
        } else {
            val errorJson = """{"error":"Campus not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }
}
