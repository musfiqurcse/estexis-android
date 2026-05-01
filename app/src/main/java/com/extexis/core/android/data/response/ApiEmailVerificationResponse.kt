package com.extexis.core.android.data.response

import com.squareup.moshi.Json

data class ApiEmailVerificationResponse(
    @param:Json(name = "message")
    val message: String,
    @param:Json(name = "email_verified")
    val emailVerified: Boolean,
)
