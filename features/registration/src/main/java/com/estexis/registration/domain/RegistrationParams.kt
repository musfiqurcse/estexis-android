package com.estexis.registration.domain

data class RegistrationParams(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val confirmPassword: String,
    val role: String,
    val countryCode: String,
    val accountType: String = "personal",
)
