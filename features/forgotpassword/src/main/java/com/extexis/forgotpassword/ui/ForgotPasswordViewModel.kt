package com.extexis.forgotpassword.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extexis.core.network.ApiResult
import com.extexis.forgotpassword.domain.SendOtpForForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpForForgotPasswordUseCase,
) : ViewModel() {

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
            when (val result = sendOtpUseCase.sendOtp(current.email)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _navigationEvent.send(ForgotPasswordNavigationEvent.ToOtp(email = current.email))
                }
                is ApiResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, emailError = result.message.ifBlank { "Something went wrong" })
                    }
                }
            }
        }
    }
}
