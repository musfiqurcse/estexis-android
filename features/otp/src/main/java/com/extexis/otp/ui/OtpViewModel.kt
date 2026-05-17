package com.extexis.otp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.extexis.core.navigation.OtpPurpose.FORGOT_PASSWORD
import com.extexis.core.navigation.OtpPurpose.REGISTRATION
import com.extexis.core.navigation.OtpPurpose.VERIFY_EXISTING_USER
import com.extexis.core.navigation.OtpRoute
import com.extexis.core.network.ApiResult
import com.extexis.core.presentation.BaseViewModel
import com.extexis.core.presentation.UiMessageEvent
import com.extexis.otp.domain.ResendOtpUseCase
import com.extexis.otp.domain.UpdatePasswordUseCase
import com.extexis.otp.domain.VerifyEmailUseCase
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
class OtpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val resendOtpUseCase: ResendOtpUseCase,
) : BaseViewModel() {

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
                if (_state.value.canResend) resend()
            }

            OtpUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(OtpNavigationEvent.Back) }
        }
    }

    private fun verify() {
        val current = _state.value

        val otpError = if (current.otp.length < 6) "Please enter the complete 6-digit code" else null

        if (current.purpose == FORGOT_PASSWORD) {
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
            when (current.purpose) {
                REGISTRATION, VERIFY_EXISTING_USER -> {
                    verifyEmail()
                }
                FORGOT_PASSWORD -> {
                    updatePassword()
                }
            }
        }
    }

    private fun verifyEmail() {
        viewModelScope.launch {
            val current = _state.value
            when (val result = verifyEmailUseCase.verifyEmail(current.email, current.otp)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _navigationEvent.send(OtpNavigationEvent.ToHome)
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    sendMessage(
                        UiMessageEvent.ToastMessage(
                            OtpErrorMapper.toUiMessage(OtpErrorCode.from(result.code))
                        )
                    )
                }
            }
        }
    }

    private fun updatePassword() {
        viewModelScope.launch {
            val current = _state.value
            when (val result = updatePasswordUseCase.updatePassword(
                current.email, current.otp, current.newPassword, current.confirmPassword
            )) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _navigationEvent.send(OtpNavigationEvent.ToLogin)
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    sendMessage(
                        UiMessageEvent.ToastMessage(
                            OtpErrorMapper.toUiMessage(OtpErrorCode.from(result.code))
                        )
                    )
                }
            }
        }
    }

    private fun resend() {
        when (_state.value.purpose) {
            REGISTRATION, VERIFY_EXISTING_USER -> resendOtpForAccountVerification()
            FORGOT_PASSWORD -> resendOtpForForgotPassword()
        }
    }

    private fun resendOtpForAccountVerification() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = resendOtpUseCase.resendForVerification(_state.value.email)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    startResendTimer()
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun resendOtpForForgotPassword() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = resendOtpUseCase.resendForForgotPassword(_state.value.email)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    startResendTimer()
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                }
            }
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
