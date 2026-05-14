package com.extexis.otp.ui

sealed class OtpUiEvent {
    data class OtpChanged(val otp: String) : OtpUiEvent()
    data class NewPasswordChanged(val password: String) : OtpUiEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : OtpUiEvent()
    object VerifyClicked : OtpUiEvent()
    object ResendClicked : OtpUiEvent()
    object BackClicked : OtpUiEvent()
}

sealed class OtpNavigationEvent {
    object Back : OtpNavigationEvent()
    object ToHome : OtpNavigationEvent()
    object ToLogin : OtpNavigationEvent()
}
