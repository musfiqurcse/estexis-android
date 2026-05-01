package com.extexis.core.android.data.request

import com.squareup.moshi.Json

data class EmailVerificationRequest(
    @param:Json(name = "email")
    val email: String,
    @param:Json(name = "code")
    val code: String,
)
