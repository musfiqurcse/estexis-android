package com.estexis.profile.data.remote

import com.estexis.core.common.ApiResult
import com.estexis.core.network.executeNoContentApiCall
import com.estexis.profile.data.api.ProfileApi
import com.estexis.profile.data.request.LogoutRequest
import javax.inject.Inject

interface ProfileRemoteSource {
    suspend fun logout(request: LogoutRequest): ApiResult<Unit>
}

class ProfileRemoteSourceImpl @Inject constructor(
    private val profileApi: ProfileApi,
) : ProfileRemoteSource {

    override suspend fun logout(request: LogoutRequest): ApiResult<Unit> {
        return executeNoContentApiCall { profileApi.logout(request) }
    }
}
