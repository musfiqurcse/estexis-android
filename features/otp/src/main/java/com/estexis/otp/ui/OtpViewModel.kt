package com.estexis.otp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.estexis.core.common.ApiResult
import com.estexis.core.domain.SendOtpForVerificationUseCase
import com.estexis.core.navigation.OtpPurpose.FORGOT_PASSWORD
import com.estexis.core.navigation.OtpPurpose.REGISTRATION
import com.estexis.core.navigation.OtpPurpose.VERIFY_EXISTING_USER
import com.estexis.core.navigation.OtpRoute
import com.estexis.core.presentation.BaseViewModel
import com.estexis.core.presentation.UiMessageEvent
import com.estexis.core.ui.error.AppErrorMapper
import com.estexis.core.ui.util.UiText
import com.estexis.core.ui.util.validation.ValidatePasswordUseCase
import com.estexis.otp.R
import com.estexis.otp.domain.SendOtpForPasswordResetUseCase
import com.estexis.otp.domain.UpdatePasswordUseCase
import com.estexis.otp.domain.VerifyEmailUseCase
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
    private val resendOtpUseCase: SendOtpForPasswordResetUseCase,
    private val sendOtpUseCase: SendOtpForVerificationUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : BaseViewModel() {

    private val route = savedStateHandle.toRoute<OtpRoute>()

    private val _state = MutableStateFlow(
        OtpState(
            email = route.email,
            lastName = route.lastName,
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
                _state.update {
                    it.copy(
                        confirmPassword = event.confirmPassword,
                        confirmPasswordError = null
                    )
                }

            OtpUiEvent.VerifyClicked -> verify()

            OtpUiEvent.ResendClicked -> {
                if (_state.value.canResend) resend()
            }

            OtpUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(OtpNavigationEvent.Back) }
        }
    }

    private fun verify() {
        when (_state.value.purpose) {
            REGISTRATION, VERIFY_EXISTING_USER -> {
                verifyEmail()
            }

            FORGOT_PASSWORD -> {
                updatePassword()
            }
        }
    }

    private fun verifyEmail() {
        viewModelScope.launch {
            if (!isValidOtp()) return@launch

            val current = _state.value

            when (val result = verifyEmailUseCase.verifyEmail(current.email, current.otp)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    sendMessage(
                        UiMessageEvent.ToastMessage(
                            UiText.StringResource(
                                R.string.otp_verification_screen_verification_done
                            )
                        )
                    )
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

    private fun updatePassword() {
        viewModelScope.launch {
            if (!isValidInput()) return@launch

            val current = _state.value
            when (val result = updatePasswordUseCase.updatePassword(
                current.email, current.otp, current.newPassword, current.confirmPassword
            )) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    sendMessage(
                        UiMessageEvent.ToastMessage(
                            UiText.StringResource(
                                R.string.otp_verification_screen_pass_reset_done
                            )
                        )
                    )
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

    private fun isValidInput(): Boolean {
        val isValidOtp = isValidOtp()
        val isValidPassword = isValidPassword()
        val isPasswordMatch = isConfirmPasswordMatched()

        return isValidPassword && isPasswordMatch && isValidOtp
    }

    private fun isValidPassword(): Boolean {
        val validationResult = validatePasswordUseCase.validatePassword(_state.value.newPassword)
        _state.update {
            it.copy(newPasswordError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

    private fun isConfirmPasswordMatched(): Boolean {
        val validationResult = validatePasswordUseCase.validateConfirmPassword(
            password = _state.value.newPassword,
            confirmPassword = _state.value.confirmPassword
        )
        _state.update {
            it.copy(confirmPasswordError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
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
            when (val result = sendOtpUseCase.send(_state.value.email, _state.value.lastName)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    startResendTimer()
                }

                is ApiResult.Error -> {
                    sendMessage(
                        UiMessageEvent.ToastMessage(
                            AppErrorMapper.map(result.code)
                        )
                    )
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun resendOtpForForgotPassword() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = resendOtpUseCase.send(
                _state.value.email, _state.value.lastName
            )
            ) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    startResendTimer()
                }

                is ApiResult.Error -> {
                    sendMessage(
                        UiMessageEvent.ToastMessage(
                            AppErrorMapper.map(result.code)
                        )
                    )
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun isValidOtp(): Boolean {
        if (_state.value.otp.length < 6) {
            _state.update {
                it.copy(
                    otpError = UiText.StringResource(
                        R.string.otp_verification_screen_please_enter_the_complete_6_digit_code
                    )
                )
            }
            return false
        }
        return true
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
