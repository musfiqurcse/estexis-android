package com.extexis.core.android.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extexis.core.android.domain.usecases.LoginUseCase
import com.extexis.core.android.util.validation.ValidateEmailUseCase
import com.extexis.core.android.util.validation.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.copy

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenUiState())
    val state: StateFlow<LoginScreenUiState> = _state

    private val _navigationEvent = Channel<LoginNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    var formState by mutableStateOf(LoginFormState())

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }
            is LoginUiEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }
            LoginUiEvent.LoginClicked -> login()

            LoginUiEvent.ForgotPasswordClicked ->
                viewModelScope.launch { _navigationEvent.send(LoginNavigationEvent.ToForgotPassword) }

            LoginUiEvent.SignUpClicked ->
                viewModelScope.launch { _navigationEvent.send(LoginNavigationEvent.ToSignUp) }

            LoginUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(LoginNavigationEvent.Back) }
        }
    }

    private fun login() {


        viewModelScope.launch {
            if (isValidInput()) {
                _state.update { it.copy(isLoading = true) }
                _navigationEvent.send(LoginNavigationEvent.ToHome)
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun isValidInput(): Boolean {
        val isValidEmail = isValidEmail()
        val isValidPassword = isValidPassword()
        return isValidEmail && isValidPassword
    }

    private fun isValidEmail(): Boolean {
        val validationResult = validateEmailUseCase.validate(formState.email)
        _state.update {
            it.copy(emailError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

    private fun isValidPassword(): Boolean {
        val validationResult = validatePasswordUseCase.validatePassword(formState.password)
        _state.update {
            it.copy(passwordError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

}
