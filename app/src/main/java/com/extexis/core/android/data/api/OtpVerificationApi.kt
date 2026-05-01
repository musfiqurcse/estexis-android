package com.extexis.core.android.data.api

import com.extexis.core.android.data.Config
import com.extexis.core.android.data.request.EmailVerificationRequest
import com.extexis.core.android.data.request.ReSendOtpRequest
import com.extexis.core.android.data.response.ApiEmailVerificationResponse
import com.extexis.core.android.data.response.ApiReSendOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OtpVerificationApi {

    @POST(Config.VERIFY_EMAIL)
    suspend fun verifyEmail(@Body request: EmailVerificationRequest): Response<ApiEmailVerificationResponse>

    @POST(Config.SEND_OTP)
    suspend fun resendOtp(@Body request: ReSendOtpRequest): Response<ApiReSendOtpResponse>

    @POST(Config.SEND_OTP_FORGOT_PASSWORD)
    suspend fun sendOtpForForgotPassword(@Body request: ReSendOtpRequest): Response<ApiReSendOtpResponse>

}
