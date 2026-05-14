package com.extexis.registration.data.remote

import com.extexis.core.network.ApiResult
import com.extexis.core.network.executeSafeApiCall
import com.extexis.registration.data.api.RegistrationApi
import com.extexis.registration.data.request.RegistrationRequest
import com.extexis.registration.data.response.ApiRegistrationResponse
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
