package com.extexis.core.android.domain.repositories

import com.extexis.core.android.data.remote.OtpVerificationRemoteSource
import com.extexis.core.android.data.request.EmailVerificationRequest
import com.extexis.core.android.data.request.ReSendOtpRequest
import com.extexis.core.android.data.response.ApiEmailVerificationResponse
import com.extexis.core.android.data.response.ApiReSendOtpResponse
import com.extexis.core.android.data.util.ApiResult
import javax.inject.Inject

interface OtpVerificationRepository {

    suspend fun sendOtp(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse>

    suspend fun sendOtpForForgotPassword(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse>

    suspend fun verifyEmail(request: EmailVerificationRequest): ApiResult<ApiEmailVerificationResponse>

}

class OtpVerificationRepositoryImpl @Inject constructor(
    private val otpVerificationRemoteSource: OtpVerificationRemoteSource
) : OtpVerificationRepository {

    override suspend fun sendOtp(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse> {
        return otpVerificationRemoteSource.resendOtp(request = request)
    }

    override suspend fun sendOtpForForgotPassword(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse> {
        return otpVerificationRemoteSource.sendOtpForForgotPassword(request = request)
    }

    override suspend fun verifyEmail(request: EmailVerificationRequest): ApiResult<ApiEmailVerificationResponse> {
        return otpVerificationRemoteSource.verifyEmail(request)
    }

}
