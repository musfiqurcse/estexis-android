package com.extexis.core.android.domain.usecases

import com.extexis.core.android.data.request.ReSendOtpRequest
import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.domain.repositories.OtpVerificationRepository
import javax.inject.Inject

interface SendOtpUseCase {

    suspend fun sendOtpForAccountVerification(email: String): ApiResult<Boolean>

    suspend fun sendOtpForForgotPassword(email: String): ApiResult<Boolean>

}

class SendOtpUseCaseImpl @Inject constructor(
    private val otpVerificationRepository: OtpVerificationRepository
) : SendOtpUseCase {

    override suspend fun sendOtpForAccountVerification(email: String):  ApiResult<Boolean> {
        return when(val result = otpVerificationRepository.sendOtp(request = ReSendOtpRequest(email))) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }

    override suspend fun sendOtpForForgotPassword(email: String): ApiResult<Boolean> {
        return when(val result = otpVerificationRepository.sendOtpForForgotPassword(request = ReSendOtpRequest(email))) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }
}
