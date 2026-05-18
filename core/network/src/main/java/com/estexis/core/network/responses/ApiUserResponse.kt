package com.estexis.core.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUserResponse(
    @field:Json(name = "public_id") val publicId: String,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "role") val role: String,
    @field:Json(name = "country_code") val countryCode: String,
    @field:Json(name = "account_type") val accountType: String,
    @field:Json(name = "status") val status: String,
)
