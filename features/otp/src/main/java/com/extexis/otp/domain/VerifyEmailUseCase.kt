package com.extexis.otp.domain

import com.extexis.core.network.ApiResult
import com.extexis.otp.data.repository.OtpRepository
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
