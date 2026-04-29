package com.extexis.core.android.presentation.login

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    object LoginClicked : LoginUiEvent()
    object ForgotPasswordClicked : LoginUiEvent()
    object SignUpClicked : LoginUiEvent()
    object BackClicked : LoginUiEvent()
}

sealed class LoginNavigationEvent {
    object Back : LoginNavigationEvent()
    object ToHome : LoginNavigationEvent()
    object ToForgotPassword : LoginNavigationEvent()
    object ToSignUp : LoginNavigationEvent()
}
