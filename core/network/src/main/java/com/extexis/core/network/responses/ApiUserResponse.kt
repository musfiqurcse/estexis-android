package com.extexis.core.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUserResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "role") val role: String,
    @field:Json(name = "is_active") val isActive: Boolean,
    @field:Json(name = "email_verified") val emailVerified: Boolean,
)
