package com.extexis.forgotpassword.ui

sealed class ForgotPasswordUiEvent {
    data class EmailChanged(val email: String) : ForgotPasswordUiEvent()
    object SubmitClicked : ForgotPasswordUiEvent()
    object BackClicked : ForgotPasswordUiEvent()
}

sealed class ForgotPasswordNavigationEvent {
    object Back : ForgotPasswordNavigationEvent()
    data class ToOtp(val email: String) : ForgotPasswordNavigationEvent()
}
