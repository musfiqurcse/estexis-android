package com.estexis.changepassword.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChangePasswordApi {

    @POST("user/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ChangePasswordResponse>
}

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)

data class ChangePasswordResponse(
    val message: String,
)
