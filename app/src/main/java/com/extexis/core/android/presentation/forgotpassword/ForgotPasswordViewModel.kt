package com.extexis.core.android.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state

    private val _navigationEvent = Channel<ForgotPasswordNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: ForgotPasswordUiEvent) {
        when (event) {
            is ForgotPasswordUiEvent.EmailChanged ->
                _state.update { it.copy(email = event.email, emailError = null) }

            ForgotPasswordUiEvent.SubmitClicked -> submit()

            ForgotPasswordUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(ForgotPasswordNavigationEvent.Back) }
        }
    }

    private fun submit() {
        val current = _state.value

        if (current.email.isBlank()) {
            _state.update { it.copy(emailError = "Email is required") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _navigationEvent.send(ForgotPasswordNavigationEvent.ToOtp(email = current.email))
            _state.update { it.copy(isLoading = false) }
        }
    }
}
