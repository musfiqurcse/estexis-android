package com.extexis.registration.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.extexis.core.network.ApiResult
import com.extexis.core.presentation.BaseViewModel
import com.extexis.core.presentation.UiMessageEvent
import com.extexis.core.ui.util.UiText
import com.extexis.core.ui.util.validation.ValidateEmailUseCase
import com.extexis.core.ui.util.validation.ValidateNonEmptyFieldUseCase
import com.extexis.core.ui.util.validation.ValidatePasswordUseCase
import com.extexis.registration.R
import com.extexis.registration.domain.RegistrationParams
import com.extexis.registration.domain.RegistrationErrorCode
import com.extexis.registration.domain.RegistrationErrorMapper
import com.extexis.registration.domain.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateNonEmptyFieldUseCase: ValidateNonEmptyFieldUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _uiState

    private val _navigationEvent = Channel<RegistrationNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    var formState by mutableStateOf(RegistrationFormState())

    fun onEvent(event: RegistrationUiEvent) {
        when (event) {
            is RegistrationUiEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }

            is RegistrationUiEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }

            is RegistrationUiEvent.ConfirmPasswordChanged -> {
                formState = formState.copy(confirmPassword = event.confirmPassword)
            }

            is RegistrationUiEvent.FirstNameChanged -> {
                formState = formState.copy(firstName = event.firstName)
            }

            is RegistrationUiEvent.LastNameChanged -> {
                formState = formState.copy(lastName = event.lastName)
            }

            is RegistrationUiEvent.PhoneNumberChanged -> {
                formState = formState.copy(phoneNumber = event.phoneNumber)
            }

            is RegistrationUiEvent.CountrySelected -> {
                formState = formState.copy(selectedCountry = event.country)
                _uiState.update { it.copy(showCountryPicker = false, countryError = null) }
            }

            is RegistrationUiEvent.RoleChanged -> {
                formState = formState.copy(selectedRole = event.role)
                _uiState.update { it.copy(roleError = null) }
            }

            RegistrationUiEvent.ShowCountryPicker ->
                _uiState.update { it.copy(showCountryPicker = true) }

            RegistrationUiEvent.DismissCountryPicker ->
                _uiState.update { it.copy(showCountryPicker = false) }

            RegistrationUiEvent.SignUpClicked -> initiateRegistration()

            RegistrationUiEvent.LoginClicked ->
                viewModelScope.launch { _navigationEvent.send(RegistrationNavigationEvent.ToLogin) }

            RegistrationUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(RegistrationNavigationEvent.Back) }
        }
    }

    private fun initiateRegistration() {
        viewModelScope.launch {
            if (isValidInput()) {
                _uiState.update { it.copy(isLoading = true, isError = false) }
                val result = registrationUseCase.register(
                    RegistrationParams(
                        firstName = formState.firstName,
                        lastName = formState.lastName,
                        email = formState.email,
                        phoneNumber = formState.phoneNumber,
                        password = formState.password,
                        confirmPassword = formState.confirmPassword,
                        countryCode = formState.selectedCountry?.code ?: "",
                        role = formState.selectedRole?.value ?: ""
                    )
                )
                when (result) {
                    is ApiResult.Success -> {
                        _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                        _navigationEvent.send(RegistrationNavigationEvent.ToOtpVerification(formState.email))
                    }

                    is ApiResult.Error -> {
                        sendMessage(
                            UiMessageEvent.ToastMessage(
                                RegistrationErrorMapper.toUiMessage(
                                    RegistrationErrorCode.from(result.code)
                                )
                            )
                        )
                        _uiState.update { it.copy(isLoading = false, isError = true) }
                    }
                }
            }
        }
    }

    private fun isValidInput(): Boolean {
        val isFirstNameValid = validateNameInput()
        val isValidEmail = isValidEmail()
        val isValidPhoneNumber = isValidPhoneNumber()
        val isCountrySelected = isCountrySelected()
        val isValidPassword = isValidPassword()
        val isPasswordMatch = isConfirmPasswordMatched()
        val isRoleSelected = isRoleSelected()
        return isFirstNameValid && isValidEmail && isValidPhoneNumber && isCountrySelected && isValidPassword && isPasswordMatch && isRoleSelected
    }

    private fun validateNameInput(): Boolean {

        val isValidFirstName = validateNonEmptyFieldUseCase.isEmpty(
            value = formState.firstName,
            errorMessageId = R.string.registration_screen_first_name_is_empty
        )

        val isValidLastName = validateNonEmptyFieldUseCase.isEmpty(
            value = formState.lastName,
            errorMessageId = R.string.registration_screen_last_name_is_empty
        )

        _uiState.update {
            it.copy(
                firstNameError = isValidFirstName.errorMessage,
                lastNameError = isValidLastName.errorMessage
            )
        }
        return isValidFirstName.isSuccessful && isValidLastName.isSuccessful
    }

    private fun isValidEmail(): Boolean {
        val validationResult = validateEmailUseCase.validate(formState.email)
        _uiState.update {
            it.copy(emailError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

    private fun isCountrySelected(): Boolean {
        val isSelected = formState.selectedCountry != null
        if (!isSelected) {
            _uiState.update { it.copy(countryError = UiText.StringResource(R.string.registration_screen_country_is_not_selected)) }
        } else {
            _uiState.update { it.copy(countryError = null) }
        }
        return isSelected
    }

    private fun isRoleSelected(): Boolean {
        val isSelected = formState.selectedRole != null
        if (!isSelected) {
            _uiState.update { it.copy(roleError = UiText.StringResource(R.string.registration_screen_role_is_not_selected)) }
        } else {
            _uiState.update { it.copy(roleError = null) }
        }
        return isSelected
    }

    private fun isValidPhoneNumber(): Boolean {
        val validationResult = validateNonEmptyFieldUseCase.isEmpty(
            formState.phoneNumber,
            R.string.error_message_invalid_phone_number)
        _uiState.update {
            it.copy(phoneNumberError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

    private fun isValidPassword(): Boolean {
        val validationResult = validatePasswordUseCase.validatePassword(formState.password)
        _uiState.update {
            it.copy(passwordError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

    private fun isConfirmPasswordMatched(): Boolean {
        val validationResult = validatePasswordUseCase.validateConfirmPassword(
            password = formState.password,
            confirmPassword = formState.confirmPassword
        )
        _uiState.update {
            it.copy(confirmPasswordError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }
}
