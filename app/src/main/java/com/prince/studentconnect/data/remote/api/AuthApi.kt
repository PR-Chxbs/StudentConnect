package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.auth.VerifyTokenRequest
import com.prince.studentconnect.data.remote.dto.auth.VerifyTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/verify")
    suspend fun verifyToken(
        @Body request: VerifyTokenRequest
    ): Response<VerifyTokenResponse>
}