package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.local.Dao.CampusDao
import com.prince.studentconnect.data.local.Entities.Campus
import com.prince.studentconnect.data.remote.api.CampusApi
import com.prince.studentconnect.data.remote.dto.campus.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CampusRepository(private val campusApi: CampusApi, private val campusDao: CampusDao) {
    suspend fun getCampuses(): List<Campus> {
        return try {
            val response = campusApi.getCampuses()
            if (response.isSuccessful) {
                val campusesFromApi = response.body()?.map { dto ->
                    Campus(
                        campus_id = dto.campus_id,
                        name = dto.name,
                        location = dto.location,
                        description = dto.description
                    )
                } ?: emptyList()

                // cache to local DB
                withContext(Dispatchers.IO) {
                    campusDao.insertCampuses(campusesFromApi)
                }

                campusesFromApi
            } else {
                // fallback to cache
                withContext(Dispatchers.IO) { campusDao.getAllCampuses() }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) { campusDao.getAllCampuses() }
        }
    }

    // 🔹 Add campus
    suspend fun addCampus(request: AddCampusRequest): Response<AddCampusResponse> {
        val response = campusApi.addCampus(request)
        if (response.isSuccessful) {
            response.body()?.let { dto ->
                val campus = Campus(
                    campus_id = dto.campus_id,
                    name = dto.name,
                    location = dto.location,
                    description = dto.description
                )
                withContext(Dispatchers.IO) {
                    campusDao.insertCampuses(listOf(campus))
                }
            }
        }
        return response
    }

    // 🔹 Update campus
    suspend fun updateCampus(request: UpdateCampusRequest, campusId: Int): Response<UpdateCampusResponse> {
        val response = campusApi.updateCampus(request, campusId)
        if (response.isSuccessful) {
            response.body()?.let { dto ->
                val updatedCampus = Campus(
                    campus_id = dto.campus_id,
                    name = dto.name,
                    location = dto.location,
                    description = dto.description
                )
                withContext(Dispatchers.IO) {
                    campusDao.updateCampus(updatedCampus)
                }
            }
        }
        return response
    }

    // 🔹 delete campus
    suspend fun deleteCampus(campusId: Int): Response<DeleteCampusResponse> {
        val response = campusApi.deleteCampus(campusId)
        if (response.isSuccessful) {
            withContext(Dispatchers.IO) {
                campusDao.getCampusById(campusId)?.let { campusDao.deleteCampus(it) }
            }
        }
        return response
    }
}
