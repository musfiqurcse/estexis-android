package com.extexis.core.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiLoginResponse(
    @param:Json(name = "access_token")
    val accessToken: String,
    @param:Json(name = "refresh_token")
    val refreshToken: String,
    @param:Json(name = "expires_at")
    val expiresAt: Long,
    @param:Json(name = "user")
    val apiUserResponse: ApiUserResponse,
)
