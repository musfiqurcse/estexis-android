package com.extexis.core.android.domain.usecases

import com.extexis.core.android.data.request.EmailVerificationRequest
import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.domain.repositories.OtpVerificationRepository
import javax.inject.Inject

interface VerifyEmailUseCase {

    suspend fun verifyEmail(email: String, code: String): ApiResult<Boolean>

}

class VerifyEmailUseCaseImpl @Inject constructor(
    private val otpVerificationRepository: OtpVerificationRepository
) : VerifyEmailUseCase {

    override suspend fun verifyEmail(
        email: String,
        code: String
    ): ApiResult<Boolean> {
        return when (val result = otpVerificationRepository.verifyEmail(
            request = EmailVerificationRequest(
                email = email,
                code = code
            )
        )) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }
}
