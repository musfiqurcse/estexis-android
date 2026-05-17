package com.extexis.forgotpassword.ui

sealed class ForgotPasswordUiEvent {
    data class EmailChanged(val email: String) : ForgotPasswordUiEvent()
    data class LastNameChanged(val lastName: String) : ForgotPasswordUiEvent()
    object SubmitClicked : ForgotPasswordUiEvent()
    object BackClicked : ForgotPasswordUiEvent()
}

sealed class ForgotPasswordNavigationEvent {
    object Back : ForgotPasswordNavigationEvent()
    data class ToOtp(val email: String, val lastName: String) : ForgotPasswordNavigationEvent()
}
