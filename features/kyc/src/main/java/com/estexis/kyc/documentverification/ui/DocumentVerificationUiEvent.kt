package com.estexis.kyc.documentverification.ui

import android.net.Uri

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
    data class PhotoTaken(val uri: Uri) : PassportVerificationUiEvent()
    object CancelCapture : PassportVerificationUiEvent()
    object FaceVerified : PassportVerificationUiEvent()
}

sealed class PassportVerificationNavigationEvent {
    object Back : PassportVerificationNavigationEvent()
    object Submitted : PassportVerificationNavigationEvent()
}
