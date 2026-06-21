package com.estexis.changepassword.ui

sealed class ChangePasswordUiEvent {
    data class CurrentPasswordChanged(val value: String) : ChangePasswordUiEvent()
    data class NewPasswordChanged(val value: String) : ChangePasswordUiEvent()
    data class ConfirmPasswordChanged(val value: String) : ChangePasswordUiEvent()
    object SaveClicked : ChangePasswordUiEvent()
    object ContinueClicked : ChangePasswordUiEvent()
    object BackClicked : ChangePasswordUiEvent()
}

sealed class ChangePasswordNavigationEvent {
    object Back : ChangePasswordNavigationEvent()
    object Success : ChangePasswordNavigationEvent()
}
