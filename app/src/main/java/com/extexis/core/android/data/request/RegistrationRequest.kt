package com.extexis.core.android.data.request

import com.squareup.moshi.Json

data class RegistrationRequest(
    @param:Json(name = "first_name")
    val firstName: String,
    @param:Json(name = "last_name")
    val lastName: String,
    @param:Json(name = "email")
    val email: String,
    @param:Json(name = "username")
    val username: String? = null,
    @param:Json(name = "password")
    val password: String,
    @param:Json(name = "confirm_password")
    val confirmPassword: String,
)
