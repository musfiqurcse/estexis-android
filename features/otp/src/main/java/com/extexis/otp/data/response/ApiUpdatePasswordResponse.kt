package com.extexis.otp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUpdatePasswordResponse(
    @Json(name = "message") val message: String,
    @Json(name = "tokens_revoked") val tokensRevoked: Int? = null,
)
