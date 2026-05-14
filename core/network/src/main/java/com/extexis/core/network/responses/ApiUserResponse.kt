package com.extexis.core.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUserResponse(
    @Json(name = "id") val id: String,
    @Json(name = "email") val email: String,
    @Json(name = "role") val role: String,
    @Json(name = "is_active") val isActive: Boolean,
    @Json(name = "email_verified") val emailVerified: Boolean,
)