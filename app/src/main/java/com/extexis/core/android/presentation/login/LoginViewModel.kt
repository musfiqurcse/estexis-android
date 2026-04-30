package com.extexis.core.android.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _navigationEvent = Channel<LoginNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged ->
                _state.update { it.copy(email = event.email, emailError = null) }

            is LoginUiEvent.PasswordChanged ->
                _state.update { it.copy(password = event.password, passwordError = null) }

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
        val current = _state.value
        val emailError = if (current.email.isBlank()) "Email is required" else null
        val passwordError = if (current.password.isBlank()) "Password is required" else null

//        if (emailError != null || passwordError != null) {
//            _state.update { it.copy(emailError = emailError, passwordError = passwordError) }
//            return
//        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _navigationEvent.send(LoginNavigationEvent.ToHome)
            _state.update { it.copy(isLoading = false) }
        }
    }
}
