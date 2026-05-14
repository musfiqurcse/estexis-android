package com.extexis.registration.data.repository

import com.extexis.core.network.ApiResult
import com.extexis.registration.data.remote.RegistrationRemoteSource
import com.extexis.registration.data.request.RegistrationRequest
import com.extexis.registration.data.response.ApiRegistrationResponse
import javax.inject.Inject

interface RegistrationRepository {
    suspend fun register(request: RegistrationRequest): ApiResult<ApiRegistrationResponse>
}

class RegistrationRepositoryImpl @Inject constructor(
    private val remoteSource: RegistrationRemoteSource,
) : RegistrationRepository {

    override suspend fun register(request: RegistrationRequest): ApiResult<ApiRegistrationResponse> {
        return remoteSource.register(request)
    }
}
