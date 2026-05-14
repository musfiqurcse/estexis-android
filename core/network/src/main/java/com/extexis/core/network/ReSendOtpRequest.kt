package com.extexis.core.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReSendOtpRequest(
    @Json(name = "email") val email: String,
)
