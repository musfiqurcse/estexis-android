package com.estexis.kyc.passport.ui

enum class PassportPhotoTarget { COVER, DATA }

data class PassportVerificationFormState(
    val passportNumber: String = "",
    val dateOfBirth: String = "",
    val expiryDate: String = "",
    val issueDate: String = "",
    val countryOfIssue: String = "",
    val coverPhotoCaptured: Boolean = false,
    val dataPhotoCaptured: Boolean = false,
)

data class PassportVerificationUiState(
    val currentStep: Int = 1,
    val captureMode: PassportPhotoTarget? = null,
    val isLoading: Boolean = false,
    val passportNumberError: String? = null,
    val dateOfBirthError: String? = null,
    val expiryDateError: String? = null,
    val issueDateError: String? = null,
    val countryError: String? = null,
) {
    val isCaptureMode: Boolean get() = captureMode != null
    val canSubmit: Boolean get() = currentStep == 2 && !isCaptureMode &&
        // submit requires both photos
        true // simplified — actual gate done via form state in viewmodel
}
