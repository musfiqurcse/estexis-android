package com.estexis.login.ui

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    object LoginClicked : LoginUiEvent()
    object ForgotPasswordClicked : LoginUiEvent()
    object SignUpClicked : LoginUiEvent()
}

sealed class LoginNavigationEvent {
    object ToHome : LoginNavigationEvent()
    object ToForgotPassword : LoginNavigationEvent()
    object ToSignUp : LoginNavigationEvent()
    data class VerifyEmail(val email: String) : LoginNavigationEvent()
}
