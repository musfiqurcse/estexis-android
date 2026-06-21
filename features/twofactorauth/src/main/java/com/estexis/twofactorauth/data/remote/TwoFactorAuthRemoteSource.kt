package com.estexis.twofactorauth.data.remote

import com.estexis.twofactorauth.data.api.TwoFactorAuthApi
import com.estexis.core.common.ApiResult
import com.estexis.core.network.executeSafeApiCall
import javax.inject.Inject

interface TwoFactorAuthRemoteSource {
    suspend fun enable(): ApiResult<Unit>
    suspend fun disable(): ApiResult<Unit>
}

class TwoFactorAuthRemoteSourceImpl @Inject constructor(
    private val api: TwoFactorAuthApi,
) : TwoFactorAuthRemoteSource {

    override suspend fun enable(): ApiResult<Unit> {
        val result = executeSafeApiCall { api.enable2fa() }
        return when (result) {
            is ApiResult.Success -> ApiResult.Success(Unit)
            is ApiResult.Error -> result
        }
    }

    override suspend fun disable(): ApiResult<Unit> {
        val result = executeSafeApiCall { api.disable2fa() }
        return when (result) {
            is ApiResult.Success -> ApiResult.Success(Unit)
            is ApiResult.Error -> result
        }
    }
}
