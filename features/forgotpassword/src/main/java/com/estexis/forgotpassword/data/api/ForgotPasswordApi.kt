package com.estexis.forgotpassword.data.api

import com.estexis.core.network.NetworkConfig
import com.estexis.core.network.ReSendOtpForgotPasswordRequest
import com.estexis.core.network.responses.ApiReSendOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotPasswordApi {

    @POST(NetworkConfig.SEND_OTP_FORGOT_PASSWORD)
    suspend fun sendOtp(@Body request: ReSendOtpForgotPasswordRequest): Response<ApiReSendOtpResponse>
}
