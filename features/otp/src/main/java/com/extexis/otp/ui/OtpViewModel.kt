package com.extexis.otp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.extexis.core.navigation.OtpPurpose
import com.extexis.core.navigation.OtpRoute
import com.extexis.core.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : BaseViewModel() {

    private val route = savedStateHandle.toRoute<OtpRoute>()

    private val _state = MutableStateFlow(
        OtpState(
            email = route.email,
            purpose = route.purpose,
        )
    )
    val state: StateFlow<OtpState> = _state

    private val _navigationEvent = Channel<OtpNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private var timerJob: Job? = null

    init {
        startResendTimer()
    }

    fun onEvent(event: OtpUiEvent) {
        when (event) {
            is OtpUiEvent.OtpChanged ->
                _state.update { it.copy(otp = event.otp, otpError = null) }

            is OtpUiEvent.NewPasswordChanged ->
                _state.update { it.copy(newPassword = event.password, newPasswordError = null) }

            is OtpUiEvent.ConfirmPasswordChanged ->
                _state.update { it.copy(confirmPassword = event.confirmPassword, confirmPasswordError = null) }

            OtpUiEvent.VerifyClicked -> verify()

            OtpUiEvent.ResendClicked -> {
                if (_state.value.canResend) {
                    _state.update { it.copy(otp = "", otpError = null) }
                    startResendTimer()
                }
            }

            OtpUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(OtpNavigationEvent.Back) }
        }
    }

    private fun verify() {
        val current = _state.value

        val otpError = if (current.otp.length < 6) "Please enter the complete 6-digit code" else null

        if (current.purpose == OtpPurpose.ForgotPassword) {
            val newPasswordError = if (current.newPassword.isBlank()) "Password is required" else null
            val confirmPasswordError = when {
                current.confirmPassword.isBlank() -> "Please confirm your password"
                current.confirmPassword != current.newPassword -> "Passwords do not match"
                else -> null
            }

            if (otpError != null || newPasswordError != null || confirmPasswordError != null) {
                _state.update {
                    it.copy(
                        otpError = otpError,
                        newPasswordError = newPasswordError,
                        confirmPasswordError = confirmPasswordError,
                    )
                }
                return
            }
        } else if (otpError != null) {
            _state.update { it.copy(otpError = otpError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val destination = when (current.purpose) {
                OtpPurpose.Registration -> OtpNavigationEvent.ToHome
                OtpPurpose.ForgotPassword -> OtpNavigationEvent.ToLogin
            }
            _navigationEvent.send(destination)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun startResendTimer() {
        timerJob?.cancel()
        _state.update { it.copy(resendTimer = 60, canResend = false) }
        timerJob = viewModelScope.launch {
            repeat(60) { tick ->
                delay(1000)
                _state.update { it.copy(resendTimer = 59 - tick) }
            }
            _state.update { it.copy(canResend = true) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
