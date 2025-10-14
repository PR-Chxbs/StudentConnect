package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.CampusApi
import com.prince.studentconnect.data.remote.dto.campus.*
import retrofit2.Response

class CampusRepository(
    private val campusApi: CampusApi
) {
    suspend fun getCampuses(): Response<GetCampusesResponse> {
        return campusApi.getCampuses()
    }

    suspend fun addCampus(addCampusRequest: AddCampusRequest): Response<Unit> {
        return campusApi.addCampus(addCampusRequest)
    }

    suspend fun updateCampus(updateCampusRequest: UpdateCampusRequest, campusId: Int): Response<Unit> {
        return campusApi.updateCampus(updateCampusRequest, campusId)
    }

    suspend fun deleteCampus(campusId: Int): Response<Unit> {
        return campusApi.deleteCampus(campusId)
    }
}