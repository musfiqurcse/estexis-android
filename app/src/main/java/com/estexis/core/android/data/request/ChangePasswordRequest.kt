package com.estexis.core.android.data.request

import com.squareup.moshi.Json

data class ChangePasswordRequest(
    @param:Json(name = "current_password")
    val currentPassword: String,
    @param:Json(name = "new_password")
    val newPassword: String,
    @param:Json(name = "confirm_password")
    val confirmPassword: String,
)
