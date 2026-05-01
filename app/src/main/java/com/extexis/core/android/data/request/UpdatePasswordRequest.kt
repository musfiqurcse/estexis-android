package com.extexis.core.android.data.request

import com.squareup.moshi.Json

data class UpdatePasswordRequest(
    @param:Json(name = "email")
    val email: String,
    @param:Json(name = "code")
    val code: String,
    @param:Json(name = "new_password")
    val newPassword: String,
    @param:Json(name = "confirm_password")
    val confirmPassword: String,
)
