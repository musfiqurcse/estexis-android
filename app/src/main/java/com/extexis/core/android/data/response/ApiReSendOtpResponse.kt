package com.extexis.core.android.data.response

import com.squareup.moshi.Json

data class ApiReSendOtpResponse(
    @param:Json(name = "message")
    val message: String,
    @param:Json(name = "expires_in_seconds")
    val expiresInSeconds: Int? = null,
    @param:Json(name = "active_otp")
    val activeOtp: Boolean? = false,
    @param:Json(name = "cooldown_remaining_seconds")
    val cooldownRemainingSeconds: Int? = null,
)
