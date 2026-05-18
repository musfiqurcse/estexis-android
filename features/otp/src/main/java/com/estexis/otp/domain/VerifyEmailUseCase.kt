package com.estexis.otp.domain

import com.estexis.core.network.ApiResult
import com.estexis.otp.data.repository.OtpRepository
import javax.inject.Inject

interface VerifyEmailUseCase {
    suspend fun verifyEmail(email: String, code: String): ApiResult<Boolean>
}

class VerifyEmailUseCaseImpl @Inject constructor(
    private val repository: OtpRepository,
) : VerifyEmailUseCase {

    override suspend fun verifyEmail(email: String, code: String): ApiResult<Boolean> {
        return repository.verifyEmail(email, code)
    }
}
