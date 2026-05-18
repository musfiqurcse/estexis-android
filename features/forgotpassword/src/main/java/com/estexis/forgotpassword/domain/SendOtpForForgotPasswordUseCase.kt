package com.estexis.forgotpassword.domain

import com.estexis.core.network.ApiResult
import com.estexis.forgotpassword.data.repository.ForgotPasswordRepository
import javax.inject.Inject

interface SendOtpForForgotPasswordUseCase {
    suspend fun sendOtp(email: String, lastName: String): ApiResult<Boolean>
}

class SendOtpForForgotPasswordUseCaseImpl @Inject constructor(
    private val repository: ForgotPasswordRepository,
) : SendOtpForForgotPasswordUseCase {

    override suspend fun sendOtp(email: String, lastName: String): ApiResult<Boolean> {
        return repository.sendOtp(email = email, lastName = lastName)
    }
}
