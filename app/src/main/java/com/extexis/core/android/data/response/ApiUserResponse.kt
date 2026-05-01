package com.extexis.core.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUserResponse(
    @param:Json(name = "id")
    val id: String,
    @param:Json(name = "email")
    val email: String,
    @param:Json(name = "role")
    val role: String,
    @param:Json(name = "is_active")
    val isActive: Boolean,
    @param:Json(name = "email_verified")
    val emailVerified: Boolean,
)
