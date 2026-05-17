package com.extexis.otp.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmailVerificationRequest(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "code") val code: String,
)
