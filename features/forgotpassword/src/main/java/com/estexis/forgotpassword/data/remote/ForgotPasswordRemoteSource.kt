package com.estexis.forgotpassword.data.remote

import com.estexis.core.common.ApiResult
import com.estexis.core.network.executeSafeApiCall
import com.estexis.core.network.request.SendOtpRequest
import com.estexis.core.network.responses.ApiReSendOtpResponse
import com.estexis.forgotpassword.data.api.ForgotPasswordApi
import javax.inject.Inject

interface ForgotPasswordRemoteSource {
    suspend fun sendOtp(request: SendOtpRequest): ApiResult<ApiReSendOtpResponse>
}

class ForgotPasswordRemoteSourceImpl @Inject constructor(
    private val forgotPasswordApi: ForgotPasswordApi,
) : ForgotPasswordRemoteSource {

    override suspend fun sendOtp(request: SendOtpRequest): ApiResult<ApiReSendOtpResponse> {
        return executeSafeApiCall { forgotPasswordApi.sendOtp(request) }
    }
}
