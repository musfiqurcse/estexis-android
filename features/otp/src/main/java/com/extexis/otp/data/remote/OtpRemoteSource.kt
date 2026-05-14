package com.extexis.otp.data.remote

import com.extexis.core.network.responses.ApiReSendOtpResponse
import com.extexis.core.network.ApiResult
import com.extexis.core.network.ReSendOtpRequest
import com.extexis.core.network.executeSafeApiCall
import com.extexis.otp.data.api.OtpApi
import com.extexis.otp.data.request.EmailVerificationRequest
import com.extexis.otp.data.request.UpdatePasswordRequest
import com.extexis.otp.data.response.ApiEmailVerificationResponse
import com.extexis.otp.data.response.ApiUpdatePasswordResponse
import javax.inject.Inject

interface OtpRemoteSource {
    suspend fun verifyEmail(request: EmailVerificationRequest): ApiResult<ApiEmailVerificationResponse>
    suspend fun resendOtp(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse>
    suspend fun resendOtpForForgotPassword(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse>
    suspend fun updatePassword(request: UpdatePasswordRequest): ApiResult<ApiUpdatePasswordResponse>
}

class OtpRemoteSourceImpl @Inject constructor(
    private val otpApi: OtpApi,
) : OtpRemoteSource {

    override suspend fun verifyEmail(request: EmailVerificationRequest): ApiResult<ApiEmailVerificationResponse> {
        return executeSafeApiCall { otpApi.verifyEmail(request) }
    }

    override suspend fun resendOtp(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse> {
        return executeSafeApiCall { otpApi.resendOtp(request) }
    }

    override suspend fun resendOtpForForgotPassword(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse> {
        return executeSafeApiCall { otpApi.resendOtpForForgotPassword(request) }
    }

    override suspend fun updatePassword(request: UpdatePasswordRequest): ApiResult<ApiUpdatePasswordResponse> {
        return executeSafeApiCall { otpApi.updatePassword(request) }
    }
}
