package com.extexis.core.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRegistrationResponse(
    @param:Json(name = "message")
    val message: String,
    @param:Json(name = "user")
    val apiUserResponse: ApiUserResponse,
    @param:Json(name = "email_sent")
    val emailSent: Boolean,
)
