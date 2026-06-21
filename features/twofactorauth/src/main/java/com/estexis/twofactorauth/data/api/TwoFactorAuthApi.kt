package com.estexis.twofactorauth.data.api

import retrofit2.Response
import retrofit2.http.POST

interface TwoFactorAuthApi {

    @POST("user/2fa/enable")
    suspend fun enable2fa(): Response<TwoFactorAuthResponse>

    @POST("user/2fa/disable")
    suspend fun disable2fa(): Response<TwoFactorAuthResponse>
}

data class TwoFactorAuthResponse(
    val message: String,
)
