package com.extexis.forgotpassword.data.api

import com.extexis.core.network.NetworkConfig
import com.extexis.core.network.ReSendOtpRequest
import com.extexis.core.network.responses.ApiReSendOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotPasswordApi {

    @POST(NetworkConfig.SEND_OTP_FORGOT_PASSWORD)
    suspend fun sendOtp(@Body request: ReSendOtpRequest): Response<ApiReSendOtpResponse>
}
