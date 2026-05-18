package com.estexis.core.network

import com.estexis.core.network.request.SendOtpRequest
import com.estexis.core.network.responses.ApiReSendOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthVerificationApi {

    @POST(NetworkConfig.SEND_OTP)
    suspend fun sendOtpForVerification(
        @Body request: SendOtpRequest,
    ): Response<ApiReSendOtpResponse>
}
