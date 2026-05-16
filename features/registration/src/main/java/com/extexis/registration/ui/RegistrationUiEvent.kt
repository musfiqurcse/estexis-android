package com.extexis.registration.ui

import com.extexis.registration.domain.AccountRole
import com.extexis.registration.domain.Country

sealed class RegistrationUiEvent {
    data class EmailChanged(val email: String) : RegistrationUiEvent()
    data class PasswordChanged(val password: String) : RegistrationUiEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegistrationUiEvent()
    data class FirstNameChanged(val firstName: String) : RegistrationUiEvent()
    data class LastNameChanged(val lastName: String) : RegistrationUiEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : RegistrationUiEvent()
    data class CountrySelected(val country: Country) : RegistrationUiEvent()
    data class RoleChanged(val role: AccountRole) : RegistrationUiEvent()
    object ShowCountryPicker : RegistrationUiEvent()
    object DismissCountryPicker : RegistrationUiEvent()
    object SignUpClicked : RegistrationUiEvent()
    object LoginClicked : RegistrationUiEvent()
    object BackClicked : RegistrationUiEvent()
}

sealed class RegistrationNavigationEvent {
    object Back : RegistrationNavigationEvent()
    object ToLogin : RegistrationNavigationEvent()
    data class ToOtpVerification(val email: String) : RegistrationNavigationEvent()
}
