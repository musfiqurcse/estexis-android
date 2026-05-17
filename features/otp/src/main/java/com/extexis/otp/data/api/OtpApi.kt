package com.extexis.otp.data.api

import com.extexis.core.network.NetworkConfig
import com.extexis.core.network.ReSendOtpForgotPasswordRequest
import com.extexis.core.network.ReSendOtpRequest
import com.extexis.core.network.responses.ApiReSendOtpResponse
import com.extexis.otp.data.request.EmailVerificationRequest
import com.extexis.otp.data.request.UpdatePasswordRequest
import com.extexis.otp.data.response.ApiEmailVerificationResponse
import com.extexis.otp.data.response.ApiUpdatePasswordResponse
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
