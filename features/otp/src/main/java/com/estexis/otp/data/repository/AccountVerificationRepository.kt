package com.estexis.otp.data.repository

import com.estexis.core.common.ApiResult
import com.estexis.core.network.request.SendOtpRequest
import com.estexis.otp.data.remote.OtpRemoteSource
import com.estexis.otp.data.request.EmailVerificationRequest
import com.estexis.otp.data.request.UpdatePasswordRequest
import javax.inject.Inject

interface AccountVerificationRepository {

    suspend fun verifyEmail(email: String, code: String): ApiResult<Boolean>

    suspend fun resendOtpForForgotPassword(email: String, lastName: String): ApiResult<Boolean>

    suspend fun updatePassword(
        email: String,
        code: String,
        newPassword: String,
        confirmPassword: String
    ): ApiResult<Boolean>
}

class AccountVerificationRepositoryImpl @Inject constructor(
    private val remoteSource: OtpRemoteSource,
) : AccountVerificationRepository {

    override suspend fun verifyEmail(email: String, code: String): ApiResult<Boolean> {
        return when (val result = remoteSource.verifyEmail(EmailVerificationRequest(email, code))) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }

    override suspend fun resendOtpForForgotPassword(
        email: String,
        lastName: String
    ): ApiResult<Boolean> {
        return when (val result = remoteSource.resendOtpForForgotPassword(
            SendOtpRequest(email = email, lastName = lastName)
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
        return when (val result = remoteSource.updatePassword(
            UpdatePasswordRequest(
                email,
                code,
                newPassword,
                confirmPassword
            )
        )) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }
}
