package com.extexis.forgotpassword.domain

import com.extexis.core.network.ApiResult
import com.extexis.forgotpassword.data.repository.ForgotPasswordRepository
import javax.inject.Inject

interface SendOtpForForgotPasswordUseCase {
    suspend fun sendOtp(email: String): ApiResult<Boolean>
}

class SendOtpForForgotPasswordUseCaseImpl @Inject constructor(
    private val repository: ForgotPasswordRepository,
) : SendOtpForForgotPasswordUseCase {

    override suspend fun sendOtp(email: String): ApiResult<Boolean> {
        return repository.sendOtp(email)
    }
}
