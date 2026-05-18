package com.estexis.forgotpassword.data.remote

import com.estexis.core.common.ApiResult
import com.estexis.core.network.ReSendOtpForgotPasswordRequest
import com.estexis.core.network.executeSafeApiCall
import com.estexis.core.network.responses.ApiReSendOtpResponse
import com.estexis.forgotpassword.data.api.ForgotPasswordApi
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
