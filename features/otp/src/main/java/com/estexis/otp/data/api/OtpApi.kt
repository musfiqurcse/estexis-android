package com.estexis.otp.data.api

import com.estexis.core.network.NetworkConfig
import com.estexis.core.network.ReSendOtpForgotPasswordRequest
import com.estexis.core.network.ReSendOtpRequest
import com.estexis.core.network.responses.ApiReSendOtpResponse
import com.estexis.otp.data.request.EmailVerificationRequest
import com.estexis.otp.data.request.UpdatePasswordRequest
import com.estexis.otp.data.response.ApiEmailVerificationResponse
import com.estexis.otp.data.response.ApiUpdatePasswordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OtpApi {

    @POST(NetworkConfig.VERIFY_EMAIL)
    suspend fun verifyEmail(@Body request: EmailVerificationRequest): Response<ApiEmailVerificationResponse>

    @POST(NetworkConfig.SEND_OTP)
    suspend fun resendOtp(@Body request: ReSendOtpRequest): Response<ApiReSendOtpResponse>

    @POST(NetworkConfig.SEND_OTP_FORGOT_PASSWORD)
    suspend fun resendOtpForForgotPassword(@Body request: ReSendOtpForgotPasswordRequest): Response<ApiReSendOtpResponse>

    @POST(NetworkConfig.RESET_PASSWORD)
    suspend fun updatePassword(@Body request: UpdatePasswordRequest): Response<ApiUpdatePasswordResponse>
}
