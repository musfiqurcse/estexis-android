package com.estexis.registration.ui

import com.estexis.core.ui.gds.Country
import com.estexis.core.ui.util.UiText
import com.estexis.registration.domain.AccountRole

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
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val selectedCountry: Country? = null,
    val selectedRole: AccountRole? = null,
)
