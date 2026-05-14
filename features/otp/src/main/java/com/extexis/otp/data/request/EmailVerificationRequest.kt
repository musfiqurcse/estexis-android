package com.extexis.otp.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmailVerificationRequest(
    @Json(name = "email") val email: String,
    @Json(name = "code") val code: String,
)
