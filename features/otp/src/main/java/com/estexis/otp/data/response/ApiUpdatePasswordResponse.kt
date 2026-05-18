package com.estexis.otp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUpdatePasswordResponse(
    @field:Json(name = "message") val message: String
)
