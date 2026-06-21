package com.estexis.twofactorauth.data.repository

import com.estexis.twofactorauth.data.remote.TwoFactorAuthRemoteSource
import com.estexis.core.common.ApiResult
import javax.inject.Inject

interface TwoFactorAuthRepository {
    suspend fun enable(): ApiResult<Unit>
    suspend fun disable(): ApiResult<Unit>
}

class TwoFactorAuthRepositoryImpl @Inject constructor(
    private val remoteSource: TwoFactorAuthRemoteSource,
) : TwoFactorAuthRepository {

    override suspend fun enable(): ApiResult<Unit> = remoteSource.enable()
    override suspend fun disable(): ApiResult<Unit> = remoteSource.disable()
}
