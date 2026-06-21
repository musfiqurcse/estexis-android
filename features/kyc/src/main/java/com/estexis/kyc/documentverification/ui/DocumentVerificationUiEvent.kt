package com.estexis.kyc.documentverification.ui

import android.net.Uri

sealed class DocumentVerificationUiEvent {
    data class DocumentNumberChanged(val value: String) : DocumentVerificationUiEvent()
    data class DateOfBirthChanged(val value: String) : DocumentVerificationUiEvent()
    data class ExpiryDateChanged(val value: String) : DocumentVerificationUiEvent()
    data class IssueDateChanged(val value: String) : DocumentVerificationUiEvent()
    data class CountryChanged(val value: String) : DocumentVerificationUiEvent()

    object ContinueClicked : DocumentVerificationUiEvent()
    object BackClicked : DocumentVerificationUiEvent()
    object SubmitClicked : DocumentVerificationUiEvent()

    data class StartCapture(val target: DocumentPhotoTarget) : DocumentVerificationUiEvent()
    data class PhotoTaken(val uri: Uri) : DocumentVerificationUiEvent()
    object CancelCapture : DocumentVerificationUiEvent()
}

sealed class PassportVerificationNavigationEvent {
    object Back : PassportVerificationNavigationEvent()
    object SubmittedBack : PassportVerificationNavigationEvent()
    data class ToFaceVerification(val submissionId: String) : PassportVerificationNavigationEvent()
}
