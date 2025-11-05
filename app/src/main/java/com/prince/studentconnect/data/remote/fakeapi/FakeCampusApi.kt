package com.prince.studentconnect.data.remote.fakeapi

import com.prince.studentconnect.data.remote.api.CampusApi
import com.prince.studentconnect.data.remote.dto.campus.AddCampusRequest
import com.prince.studentconnect.data.remote.dto.campus.AddCampusResponse
import com.prince.studentconnect.data.remote.dto.campus.Campus
import com.prince.studentconnect.data.remote.dto.campus.DeleteCampusResponse
import com.prince.studentconnect.data.remote.dto.campus.GetCampusesResponse
import com.prince.studentconnect.data.remote.dto.campus.UpdateCampusRequest
import com.prince.studentconnect.data.remote.dto.campus.UpdateCampusResponse
import com.prince.studentconnect.data.remote.fakeapi.fakedata.sampleCampuses
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class FakeCampusApi : CampusApi {

    // In-memory storage for campuses
    private var campuses: MutableList<Campus> = sampleCampuses.toMutableList()
    private var nextId = 4

    override suspend fun getCampuses(): Response<List<GetCampusesResponse>> {
        val returnCampusesMutableList: MutableList<GetCampusesResponse>  = mutableListOf()

        campuses.forEach { campus ->
            val newCampus = GetCampusesResponse(
                campus_id = campus.campus_id,
                name = campus.name,
                location = campus.location,
                campus_image_url = campus.campus_image_url
            )

            returnCampusesMutableList.add(newCampus)
        }
        return Response.success(returnCampusesMutableList)
    }

    override suspend fun addCampus(request: AddCampusRequest): Response<AddCampusResponse> {
        val newCampus = Campus(
            campus_id = nextId++,
            name = request.name,
            location = request.location,
            campus_image_url = request.campus_image_url
        )
        campuses.add(newCampus)
        return Response.success(AddCampusResponse(true, newCampus))
    }

    override suspend fun updateCampus(request: UpdateCampusRequest, campusId: Int): Response<UpdateCampusResponse> {
        val campusIndex = campuses.indexOfFirst { it.campus_id == campusId }
        return if (campusIndex != -1) {
            val oldCampus = campuses[campusIndex]
            campuses[campusIndex] = oldCampus.copy(
                name = request.name,
                location = request.location,
                campus_image_url = request.campus_image_url
            )
            Response.success(UpdateCampusResponse(true, campuses[campusIndex]))
        } else {
            val errorJson = """{"error":"Campus not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun deleteCampus(campusId: Int): Response<DeleteCampusResponse> {
        val removed = campuses.removeIf { it.campus_id == campusId }
        return if (removed) {
            Response.success(DeleteCampusResponse(true, "Campus successfully deactivated"))
        } else {
            val errorJson = """{"error":"Campus not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }
}
