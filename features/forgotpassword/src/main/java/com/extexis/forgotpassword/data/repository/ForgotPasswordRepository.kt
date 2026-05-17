package com.extexis.forgotpassword.data.repository

import com.extexis.core.network.ApiResult
import com.extexis.core.network.ReSendOtpForgotPasswordRequest
import com.extexis.core.network.ReSendOtpRequest
import com.extexis.forgotpassword.data.remote.ForgotPasswordRemoteSource
import javax.inject.Inject

interface ForgotPasswordRepository {
    suspend fun sendOtp(email: String, lastName: String): ApiResult<Boolean>
}

class ForgotPasswordRepositoryImpl @Inject constructor(
    private val remoteSource: ForgotPasswordRemoteSource,
) : ForgotPasswordRepository {

    override suspend fun sendOtp(email: String, lastName: String): ApiResult<Boolean> {
        return when (val result = remoteSource.sendOtp(
            ReSendOtpForgotPasswordRequest(
                email = email,
                lastName = lastName
            )
        )) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }
}
