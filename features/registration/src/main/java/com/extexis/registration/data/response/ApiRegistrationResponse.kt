package com.extexis.registration.data.response

import com.extexis.core.network.responses.ApiUserResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRegistrationResponse(
    @field:Json(name = "message") val message: String,
    @field:Json(name = "user") val user: ApiUserResponse,
    @field:Json(name = "email_sent") val emailSent: Boolean,
)
