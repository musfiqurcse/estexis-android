package com.extexis.core.android.presentation.registration

data class RegistrationState(
    val email: String = "info.anikdey003@gmail.com",
    val password: String = "",
    val confirmPassword: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val company: String = "",
    val contactPersonName: String = "",
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val phoneNumberError: String? = null,
)
