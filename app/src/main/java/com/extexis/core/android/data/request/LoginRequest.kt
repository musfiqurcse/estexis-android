package com.extexis.core.android.data.request

import com.squareup.moshi.Json

data class LoginRequest(
    @param:Json(name = "identifier")
    val identifier: String,
    @param:Json(name = "password")
    val password: String,
)
