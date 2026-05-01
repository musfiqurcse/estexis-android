package com.extexis.core.android.data.remote

import com.extexis.core.android.data.api.OtpVerificationApi
import com.extexis.core.android.data.request.EmailVerificationRequest
import com.extexis.core.android.data.request.ReSendOtpRequest
import com.extexis.core.android.data.response.ApiEmailVerificationResponse
import com.extexis.core.android.data.response.ApiReSendOtpResponse
import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.data.util.executeSafeApiCall
import javax.inject.Inject

interface OtpVerificationRemoteSource {

    suspend fun verifyEmail(request: EmailVerificationRequest): ApiResult<ApiEmailVerificationResponse>

    suspend fun sendOtpForForgotPassword(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse>

    suspend fun resendOtp(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse>

}

class OtpVerificationRemoteSourceImpl @Inject constructor(
    private val otpVerificationApi: OtpVerificationApi,
) : OtpVerificationRemoteSource {
    override suspend fun verifyEmail(request: EmailVerificationRequest): ApiResult<ApiEmailVerificationResponse> {
        return executeSafeApiCall { otpVerificationApi.verifyEmail(request = request) }
    }

    override suspend fun sendOtpForForgotPassword(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse> {
        return executeSafeApiCall { otpVerificationApi.sendOtpForForgotPassword(request) }
    }

    override suspend fun resendOtp(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse> {
        return executeSafeApiCall { otpVerificationApi.resendOtp(request) }
    }

}
