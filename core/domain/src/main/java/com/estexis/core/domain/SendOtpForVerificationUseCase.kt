package com.estexis.core.domain

import com.estexis.core.common.ApiResult
import javax.inject.Inject

interface SendOtpForVerificationUseCase {

    suspend fun send(email: String, lastName: String): ApiResult<Boolean>
}

class SendOtpForVerificationUseCaseImpl @Inject constructor(
    private val repository: AuthVerificationRepository,
) : SendOtpForVerificationUseCase {

    override suspend fun send(email: String, lastName: String): ApiResult<Boolean> {
        return repository.sendOtpForVerification(email = email, lastName = lastName)
    }
}
