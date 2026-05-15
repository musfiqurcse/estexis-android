package com.extexis.forgotpassword.data.remote

import com.extexis.core.network.ApiResult
import com.extexis.core.network.ReSendOtpRequest
import com.extexis.core.network.executeSafeApiCall
import com.extexis.core.network.responses.ApiReSendOtpResponse
import com.extexis.forgotpassword.data.api.ForgotPasswordApi
import javax.inject.Inject

interface ForgotPasswordRemoteSource {
    suspend fun sendOtp(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse>
}

class ForgotPasswordRemoteSourceImpl @Inject constructor(
    private val forgotPasswordApi: ForgotPasswordApi,
) : ForgotPasswordRemoteSource {

    override suspend fun sendOtp(request: ReSendOtpRequest): ApiResult<ApiReSendOtpResponse> {
        return executeSafeApiCall { forgotPasswordApi.sendOtp(request) }
    }
}
