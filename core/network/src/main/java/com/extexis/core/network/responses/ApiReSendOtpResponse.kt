package com.extexis.core.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiReSendOtpResponse(
    @Json(name = "message") val message: String,
    @Json(name = "expires_in_seconds") val expiresInSeconds: Int? = null,
    @Json(name = "active_otp") val activeOtp: Boolean? = false,
    @Json(name = "cooldown_remaining_seconds") val cooldownRemainingSeconds: Int? = null,
)