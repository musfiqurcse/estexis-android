package com.extexis.otp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiEmailVerificationResponse(
    @Json(name = "message") val message: String,
)
