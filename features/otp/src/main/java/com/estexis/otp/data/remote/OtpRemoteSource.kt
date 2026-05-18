package com.estexis.otp.data.remote

import com.estexis.core.common.ApiResult
import com.estexis.core.network.executeSafeApiCall
import com.estexis.core.network.request.SendOtpRequest
import com.estexis.core.network.responses.ApiReSendOtpResponse
import com.estexis.otp.data.api.OtpApi
import com.estexis.otp.data.request.EmailVerificationRequest
import com.estexis.otp.data.request.UpdatePasswordRequest
import com.estexis.otp.data.response.ApiEmailVerificationResponse
import com.estexis.otp.data.response.ApiUpdatePasswordResponse
import javax.inject.Inject

interface OtpRemoteSource {
    suspend fun verifyEmail(request: EmailVerificationRequest): ApiResult<ApiEmailVerificationResponse>

    suspend fun resendOtpForForgotPassword(request: SendOtpRequest): ApiResult<ApiReSendOtpResponse>

    suspend fun updatePassword(request: UpdatePasswordRequest): ApiResult<ApiUpdatePasswordResponse>
}

class OtpRemoteSourceImpl @Inject constructor(
    private val otpApi: OtpApi,
) : OtpRemoteSource {

    override suspend fun verifyEmail(request: EmailVerificationRequest): ApiResult<ApiEmailVerificationResponse> {
        return executeSafeApiCall { otpApi.verifyEmail(request) }
    }

    override suspend fun resendOtpForForgotPassword(request: SendOtpRequest): ApiResult<ApiReSendOtpResponse> {
        return executeSafeApiCall { otpApi.resendOtpForForgotPassword(request) }
    }

    override suspend fun updatePassword(request: UpdatePasswordRequest): ApiResult<ApiUpdatePasswordResponse> {
        return executeSafeApiCall { otpApi.updatePassword(request) }
    }
}
