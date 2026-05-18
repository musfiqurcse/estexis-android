package com.estexis.otp.data.repository

import com.estexis.core.network.ApiResult
import com.estexis.core.network.ReSendOtpForgotPasswordRequest
import com.estexis.core.network.ReSendOtpRequest
import com.estexis.otp.data.remote.OtpRemoteSource
import com.estexis.otp.data.request.EmailVerificationRequest
import com.estexis.otp.data.request.UpdatePasswordRequest
import javax.inject.Inject

interface OtpRepository {
    suspend fun verifyEmail(email: String, code: String): ApiResult<Boolean>
    suspend fun resendOtp(email: String): ApiResult<Boolean>
    suspend fun resendOtpForForgotPassword(email: String, lastName: String): ApiResult<Boolean>
    suspend fun updatePassword(email: String, code: String, newPassword: String, confirmPassword: String): ApiResult<Boolean>
}

class OtpRepositoryImpl @Inject constructor(
    private val remoteSource: OtpRemoteSource,
) : OtpRepository {

    override suspend fun verifyEmail(email: String, code: String): ApiResult<Boolean> {
        return when (val result = remoteSource.verifyEmail(EmailVerificationRequest(email, code))) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }

    override suspend fun resendOtp(email: String): ApiResult<Boolean> {
        return when (val result = remoteSource.resendOtp(ReSendOtpRequest(email = email))) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }

    override suspend fun resendOtpForForgotPassword(email: String, lastName: String): ApiResult<Boolean> {
        return when (val result = remoteSource.resendOtpForForgotPassword(
            ReSendOtpForgotPasswordRequest(email = email, lastName = lastName)
        )) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }

    override suspend fun updatePassword(
        email: String,
        code: String,
        newPassword: String,
        confirmPassword: String,
    ): ApiResult<Boolean> {
        return when (val result = remoteSource.updatePassword(UpdatePasswordRequest(email, code, newPassword, confirmPassword))) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }
}
