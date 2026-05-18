package com.estexis.registration.data.remote

import com.estexis.core.network.ApiResult
import com.estexis.core.network.executeSafeApiCall
import com.estexis.registration.data.api.RegistrationApi
import com.estexis.registration.data.request.RegistrationRequest
import com.estexis.registration.data.response.ApiRegistrationResponse
import javax.inject.Inject

interface RegistrationRemoteSource {
    suspend fun register(request: RegistrationRequest): ApiResult<ApiRegistrationResponse>
}

class RegistrationRemoteSourceImpl @Inject constructor(
    private val registrationApi: RegistrationApi,
) : RegistrationRemoteSource {

    override suspend fun register(request: RegistrationRequest): ApiResult<ApiRegistrationResponse> {
        return executeSafeApiCall { registrationApi.register(request) }
    }
}
