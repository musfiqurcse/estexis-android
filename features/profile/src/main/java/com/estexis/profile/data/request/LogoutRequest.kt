package com.estexis.profile.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutRequest(
    @field:Json(name = "refresh_token") val refreshToken: String,
)
