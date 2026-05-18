package com.estexis.verifyexistinguser.ui

sealed class ExistingUserVerificationUiEvent {
    data class EmailChanged(val email: String) : ExistingUserVerificationUiEvent()
    data class LastNameChanged(val lastName: String) : ExistingUserVerificationUiEvent()
    object SubmitClicked : ExistingUserVerificationUiEvent()
    object BackClicked : ExistingUserVerificationUiEvent()
}

sealed class ExistingUserVerificationNavigationEvent {
    object Back : ExistingUserVerificationNavigationEvent()
    data class ToOtp(val email: String, val lastName: String) : ExistingUserVerificationNavigationEvent()
}
