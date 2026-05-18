package com.estexis.otp.domain

import com.estexis.core.common.ApiResult
import com.estexis.otp.data.repository.AccountVerificationRepository
import javax.inject.Inject

interface SendOtpForPasswordResetUseCase {

    suspend fun send(email: String, lastName: String): ApiResult<Boolean>
}

class SendOtpForPasswordResetUseCaseImpl @Inject constructor(
    private val repository: AccountVerificationRepository,
) : SendOtpForPasswordResetUseCase {

    override suspend fun send(email: String, lastName: String): ApiResult<Boolean> {
        return repository.resendOtpForForgotPassword(email = email, lastName = lastName)
    }
}
