package com.estexis.twofactorauth.domain

import com.estexis.twofactorauth.data.repository.TwoFactorAuthRepository
import com.estexis.core.common.ApiResult
import javax.inject.Inject

interface ToggleTwoFactorAuthUseCase {
    suspend fun enable(): ApiResult<Unit>
    suspend fun disable(): ApiResult<Unit>
}

class ToggleTwoFactorAuthUseCaseImpl @Inject constructor(
    private val repository: TwoFactorAuthRepository,
) : ToggleTwoFactorAuthUseCase {

    override suspend fun enable(): ApiResult<Unit> = repository.enable()
    override suspend fun disable(): ApiResult<Unit> = repository.disable()
}
