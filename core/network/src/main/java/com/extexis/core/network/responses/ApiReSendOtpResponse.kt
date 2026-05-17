package com.extexis.core.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiReSendOtpResponse(
    @field:Json(name = "message") val message: String
)
