package com.estexis.login.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.estexis.core.common.ApiResult
import com.estexis.core.presentation.BaseViewModel
import com.estexis.core.presentation.UiMessageEvent
import com.estexis.core.ui.util.validation.ValidateEmailUseCase
import com.estexis.core.ui.util.validation.ValidatePasswordUseCase
import com.estexis.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,

) : BaseViewModel() {

    private val _state = MutableStateFlow(LoginScreenUiState())
    val state: StateFlow<LoginScreenUiState> = _state

    private val _navigationEvent = Channel<LoginNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    var formState by mutableStateOf(LoginFormState())

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> formState = formState.copy(email = event.email)
            is LoginUiEvent.PasswordChanged -> formState = formState.copy(password = event.password)
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
            if (!isValidInput()) return@launch
            _state.update { it.copy(isLoading = true) }
            when (val result = loginUseCase.login(formState.email, formState.password)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _navigationEvent.send(LoginNavigationEvent.ToHome)
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, isError = true) }
                    val message = LoginErrorMapper.map(result.code)

                    if (result.code == LoginErrorMapper.NOT_VERIFIED) {
                        sendMessage(
                            UiMessageEvent.ToastMessage(errorMessage = message)
                        )
                        _navigationEvent.send(LoginNavigationEvent.VerifyEmail(formState.email))
                    } else {
                        sendMessage(UiMessageEvent.ToastMessage(message))
                    }
                }
            }
        }
    }

    private fun isValidInput(): Boolean {
        val isValidEmail = isValidEmail()
        val isValidPassword = isValidPassword()
        return isValidEmail && isValidPassword
    }

    private fun isValidEmail(): Boolean {
        val result = validateEmailUseCase.validate(formState.email)
        _state.update { it.copy(emailError = result.errorMessage) }
        return result.isSuccessful
    }

    private fun isValidPassword(): Boolean {
        val result = validatePasswordUseCase.validatePassword(formState.password)
        _state.update { it.copy(passwordError = result.errorMessage) }
        return result.isSuccessful
    }
}
