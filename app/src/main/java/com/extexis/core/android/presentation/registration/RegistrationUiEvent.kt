package com.extexis.core.android.presentation.registration

sealed class RegistrationUiEvent {
    data class EmailChanged(val email: String) : RegistrationUiEvent()
    data class PasswordChanged(val password: String) : RegistrationUiEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegistrationUiEvent()
    data class FirstNameChanged(val firstName: String) : RegistrationUiEvent()
    data class LastNameChanged(val lastName: String) : RegistrationUiEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : RegistrationUiEvent()
    data class CompanyChanged(val company: String) : RegistrationUiEvent()
    data class ContactPersonNameChanged(val contactPersonName: String) : RegistrationUiEvent()
    object SignUpClicked : RegistrationUiEvent()
    object LoginClicked : RegistrationUiEvent()
    object BackClicked : RegistrationUiEvent()
}

sealed class RegistrationNavigationEvent {
    object Back : RegistrationNavigationEvent()
    object ToLogin : RegistrationNavigationEvent()
    object ToHome : RegistrationNavigationEvent()
}
