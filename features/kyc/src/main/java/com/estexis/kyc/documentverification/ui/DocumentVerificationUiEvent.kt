package com.estexis.kyc.documentverification.ui

sealed class PassportVerificationUiEvent {
    data class PassportNumberChanged(val value: String) : PassportVerificationUiEvent()
    data class DateOfBirthChanged(val value: String) : PassportVerificationUiEvent()
    data class ExpiryDateChanged(val value: String) : PassportVerificationUiEvent()
    data class IssueDateChanged(val value: String) : PassportVerificationUiEvent()
    data class CountryChanged(val value: String) : PassportVerificationUiEvent()

    object ContinueClicked : PassportVerificationUiEvent()
    object BackClicked : PassportVerificationUiEvent()
    object SubmitClicked : PassportVerificationUiEvent()

    data class StartCapture(val target: DocumentPhotoTarget) : PassportVerificationUiEvent()
    object TakePhotoClicked : PassportVerificationUiEvent()
    object CancelCapture : PassportVerificationUiEvent()
}

sealed class PassportVerificationNavigationEvent {
    object Back : PassportVerificationNavigationEvent()
    object Submitted : PassportVerificationNavigationEvent()
}
