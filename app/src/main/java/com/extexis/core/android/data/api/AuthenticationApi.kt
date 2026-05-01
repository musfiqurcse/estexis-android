package com.extexis.core.android.data.api

import com.extexis.core.android.data.Config
import com.extexis.core.android.data.request.LoginRequest
import com.extexis.core.android.data.request.RefreshTokenRequest
import com.extexis.core.android.data.request.RegistrationRequest
import com.extexis.core.android.data.request.UpdatePasswordRequest
import com.extexis.core.android.data.response.ApiLoginResponse
import com.extexis.core.android.data.response.ApiRegistrationResponse
import com.extexis.core.android.data.response.ApiTokenResponse
import com.extexis.core.android.data.response.ApiUpdatePasswordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {

    @POST(Config.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<ApiLoginResponse>

    @POST(Config.REGISTER)
    suspend fun register(@Body request: RegistrationRequest): Response<ApiRegistrationResponse>

    @POST(Config.RESET_PASSWORD)
    suspend fun updatePassword(@Body request: UpdatePasswordRequest): Response<ApiUpdatePasswordResponse>

    @POST(Config.REFRESH_TOKEN)
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<ApiTokenResponse>

}
