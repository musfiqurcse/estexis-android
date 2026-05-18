package com.estexis.registration.data.api

import com.estexis.core.network.NetworkConfig
import com.estexis.registration.data.request.RegistrationRequest
import com.estexis.registration.data.response.ApiRegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {

    @POST(NetworkConfig.REGISTER)
    suspend fun register(@Body request: RegistrationRequest): Response<ApiRegistrationResponse>
}
