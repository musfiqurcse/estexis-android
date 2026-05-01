package com.extexis.core.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUpdatePasswordResponse(
    @param:Json(name = "message")
    val message: String,
    @param:Json(name = "tokens_revoked")
    val tokensRevoked: Int? = null,
)
