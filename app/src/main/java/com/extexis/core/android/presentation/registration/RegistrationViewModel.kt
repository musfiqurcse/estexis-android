package com.extexis.core.android.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _state

    private val _navigationEvent = Channel<RegistrationNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: RegistrationUiEvent) {
        when (event) {
            is RegistrationUiEvent.EmailChanged ->
                _state.update { it.copy(email = event.email, emailError = null) }

            is RegistrationUiEvent.PasswordChanged ->
                _state.update { it.copy(password = event.password, passwordError = null) }

            is RegistrationUiEvent.ConfirmPasswordChanged ->
                _state.update { it.copy(confirmPassword = event.confirmPassword, confirmPasswordError = null) }

            is RegistrationUiEvent.FirstNameChanged ->
                _state.update { it.copy(firstName = event.firstName, firstNameError = null) }

            is RegistrationUiEvent.LastNameChanged ->
                _state.update { it.copy(lastName = event.lastName, lastNameError = null) }

            is RegistrationUiEvent.PhoneNumberChanged ->
                _state.update { it.copy(phoneNumber = event.phoneNumber, phoneNumberError = null) }

            is RegistrationUiEvent.CompanyChanged ->
                _state.update { it.copy(company = event.company) }

            is RegistrationUiEvent.ContactPersonNameChanged ->
                _state.update { it.copy(contactPersonName = event.contactPersonName) }

            RegistrationUiEvent.SignUpClicked -> signUp()

            RegistrationUiEvent.LoginClicked ->
                viewModelScope.launch { _navigationEvent.send(RegistrationNavigationEvent.ToLogin) }

            RegistrationUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(RegistrationNavigationEvent.Back) }
        }
    }

    private fun signUp() {
        val current = _state.value

        val emailError = if (current.email.isBlank()) "Email is required" else null
        val passwordError = if (current.password.isBlank()) "Password is required" else null
        val confirmPasswordError = when {
            current.confirmPassword.isBlank() -> "Please confirm your password"
            current.confirmPassword != current.password -> "Passwords do not match"
            else -> null
        }
        val firstNameError = if (current.firstName.isBlank()) "First name is required" else null
        val lastNameError = if (current.lastName.isBlank()) "Last name is required" else null
        val phoneNumberError = if (current.phoneNumber.isBlank()) "Phone number is required" else null

        if (listOf(emailError, passwordError, confirmPasswordError, firstNameError, lastNameError, phoneNumberError).any { it != null }) {
            _state.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError,
                    firstNameError = firstNameError,
                    lastNameError = lastNameError,
                    phoneNumberError = phoneNumberError,
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _navigationEvent.send(RegistrationNavigationEvent.ToHome)
            _state.update { it.copy(isLoading = false) }
        }
    }
}
