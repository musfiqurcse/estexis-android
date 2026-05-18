package com.estexis.core.network.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendOtpRequest(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "last_name") val lastName: String,
)
