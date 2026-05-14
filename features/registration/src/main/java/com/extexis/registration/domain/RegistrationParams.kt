package com.extexis.registration.domain

data class RegistrationParams(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val confirmPassword: String,
)
