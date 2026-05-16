package com.extexis.registration.ui

import com.extexis.core.ui.util.UiText
import com.extexis.registration.domain.AccountRole
import com.extexis.registration.domain.Country

data class RegistrationState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val showCountryPicker: Boolean = false,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
    val firstNameError: UiText? = null,
    val lastNameError: UiText? = null,
    val phoneNumberError: UiText? = null,
    val countryError: UiText? = null,
    val roleError: UiText? = null,
)

data class RegistrationFormState(
    val firstName: String = "Anik",
    val lastName: String = "Dey",
    val email: String = "info.anikdey003+5@gmail.com",
    val phoneNumber: String = "01917986107",
    val password: String = "1qazZAQ!",
    val confirmPassword: String = "1qazZAQ!",
    val selectedCountry: Country? = null,
    val selectedRole: AccountRole? = null,
)
