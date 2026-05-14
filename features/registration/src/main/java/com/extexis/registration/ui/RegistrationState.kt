package com.extexis.registration.ui

import com.extexis.core.ui.util.UiText

data class RegistrationState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
    val firstNameError: UiText? = null,
    val lastNameError: UiText? = null,
    val phoneNumberError: UiText? = null,
)

data class RegistrationFormState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)
