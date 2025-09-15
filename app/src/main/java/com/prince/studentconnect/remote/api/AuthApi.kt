package com.prince.studentconnect.remote.api

import com.prince.studentconnect.remote.dto.auth.VerifyTokenRequest
import com.prince.studentconnect.remote.dto.auth.VerifyTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/verify")
    suspend fun verifyToken(
        @Body request: VerifyTokenRequest
    ): Response<VerifyTokenResponse>
}