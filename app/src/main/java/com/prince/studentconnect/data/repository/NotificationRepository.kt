package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.NotificationApi
import com.prince.studentconnect.data.remote.dto.notification.CreateDeviceTokenRequest
import com.prince.studentconnect.data.remote.dto.notification.CreateDeviceTokenResponse
import retrofit2.Response

class NotificationRepository(
    private val notificationApi: NotificationApi
) {
    suspend fun createDeviceToken(createDeviceTokenRequest: CreateDeviceTokenRequest): Response<CreateDeviceTokenResponse> {
        return notificationApi.createDeviceToken(createDeviceTokenRequest)
    }
}