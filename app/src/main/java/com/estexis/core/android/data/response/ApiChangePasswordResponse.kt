package com.estexis.core.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiChangePasswordResponse(
    @param:Json(name = "message")
    val message: String,
    @param:Json(name = "sessions_revoked")
    val sessionsRevoked: Int,
)
