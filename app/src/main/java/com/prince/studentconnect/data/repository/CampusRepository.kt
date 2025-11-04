package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.CampusApi
import com.prince.studentconnect.data.remote.dto.campus.*
import retrofit2.Response

class CampusRepository(
    private val campusApi: CampusApi
) {
    suspend fun getCampuses(): Response<List<GetCampusesResponse>> {
        return campusApi.getCampuses()
    }

    suspend fun addCampus(addCampusRequest: AddCampusRequest): Response<AddCampusResponse> {
        return campusApi.addCampus(addCampusRequest)
    }

    suspend fun updateCampus(updateCampusRequest: UpdateCampusRequest, campusId: Int): Response<UpdateCampusResponse> {
        return campusApi.updateCampus(updateCampusRequest, campusId)
    }

    suspend fun deleteCampus(campusId: Int): Response<DeleteCampusResponse> {
        return campusApi.deleteCampus(campusId)
    }
}