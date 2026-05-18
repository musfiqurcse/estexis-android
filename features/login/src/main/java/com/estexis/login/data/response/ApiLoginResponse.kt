package com.estexis.login.data.response

import com.estexis.core.network.responses.ApiUserResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiLoginResponse(
    @field:Json(name = "access_token") val accessToken: String,
    @field:Json(name = "refresh_token") val refreshToken: String,
    @field:Json(name = "token_type") val tokenType: String,
    @field:Json(name = "user") val user: ApiUserResponse,
)
