package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.notification.CreateDeviceTokenRequest
import com.prince.studentconnect.data.remote.dto.notification.CreateDeviceTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationApi {
    @POST("notifications/devices")
    suspend fun createDeviceToken(
        @Body request: CreateDeviceTokenRequest
    ): Response<CreateDeviceTokenResponse>
}