package com.extexis.forgotpassword.data.remote

import com.extexis.core.network.ApiResult
import com.extexis.core.network.ReSendOtpForgotPasswordRequest
import com.extexis.core.network.executeSafeApiCall
import com.extexis.core.network.responses.ApiReSendOtpResponse
import com.extexis.forgotpassword.data.api.ForgotPasswordApi
import javax.inject.Inject

interface ForgotPasswordRemoteSource {
    suspend fun sendOtp(request: ReSendOtpForgotPasswordRequest): ApiResult<ApiReSendOtpResponse>
}

class ForgotPasswordRemoteSourceImpl @Inject constructor(
    private val forgotPasswordApi: ForgotPasswordApi,
) : ForgotPasswordRemoteSource {

    override suspend fun sendOtp(request: ReSendOtpForgotPasswordRequest): ApiResult<ApiReSendOtpResponse> {
        return executeSafeApiCall { forgotPasswordApi.sendOtp(request) }
    }
}
