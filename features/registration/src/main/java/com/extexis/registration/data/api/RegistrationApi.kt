package com.extexis.registration.data.api

import com.extexis.core.network.NetworkConfig
import com.extexis.registration.data.request.RegistrationRequest
import com.extexis.registration.data.response.ApiRegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {

    @POST(NetworkConfig.REGISTER)
    suspend fun register(@Body request: RegistrationRequest): Response<ApiRegistrationResponse>
}
