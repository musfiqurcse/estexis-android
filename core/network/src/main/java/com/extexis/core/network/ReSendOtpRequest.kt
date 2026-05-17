package com.extexis.core.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReSendOtpRequest(
    @field:Json(name = "email") val email: String
)

@JsonClass(generateAdapter = true)
data class ReSendOtpForgotPasswordRequest(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "last_name") val lastName: String,
)
