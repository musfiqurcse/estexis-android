package com.estexis.registration.data.repository

import com.estexis.core.network.ApiResult
import com.estexis.registration.data.remote.RegistrationRemoteSource
import com.estexis.registration.data.request.RegistrationRequest
import com.estexis.registration.data.response.ApiRegistrationResponse
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
