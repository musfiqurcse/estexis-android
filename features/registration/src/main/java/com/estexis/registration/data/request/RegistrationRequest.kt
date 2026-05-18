package com.estexis.registration.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationRequest(
    @field:Json(name = "first_name") val firstName: String,
    @field:Json(name = "last_name") val lastName: String,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "password") val password: String,
    @field:Json(name = "phone_number") val phoneNumber: String,
    @field:Json(name = "confirm_password") val confirmPassword: String,
    @field:Json(name = "role") val role: String,
    @field:Json(name = "country_code") val countryCode: String,
    @field:Json(name = "account_type") val accountType: String
)
