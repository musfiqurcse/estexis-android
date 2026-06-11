package com.estexis.profile.data.api

import com.estexis.core.network.NetworkConfig
import com.estexis.profile.data.request.LogoutRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileApi {

    @POST(NetworkConfig.LOG_OUT)
    suspend fun logout(@Body request: LogoutRequest): Response<Unit>
}
