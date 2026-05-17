package com.extexis.otp.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdatePasswordRequest(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "code") val code: String,
    @field:Json(name = "new_password") val newPassword: String,
    @field:Json(name = "confirm_password") val confirmPassword: String,
)
