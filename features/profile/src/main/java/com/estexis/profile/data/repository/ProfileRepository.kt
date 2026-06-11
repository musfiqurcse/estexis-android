package com.estexis.profile.data.repository

import com.estexis.core.common.ApiResult
import com.estexis.profile.data.remote.ProfileRemoteSource
import com.estexis.profile.data.request.LogoutRequest
import javax.inject.Inject

interface ProfileRepository {
    suspend fun logout(request: LogoutRequest): ApiResult<Unit>
}

class ProfileRepositoryImpl @Inject constructor(
    private val remoteSource: ProfileRemoteSource,
) : ProfileRepository {

    override suspend fun logout(request: LogoutRequest): ApiResult<Unit> {
        return remoteSource.logout(request)
    }
}
