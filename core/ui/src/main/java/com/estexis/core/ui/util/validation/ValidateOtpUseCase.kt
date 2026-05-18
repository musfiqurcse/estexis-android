package com.estexis.core.ui.util.validation

import com.estexis.core.ui.R
import com.estexis.core.ui.util.UiText
import javax.inject.Inject

interface ValidateOtpUseCase {

    fun validate(otp: String): ValidationResult
}

class ValidateOtpUseCaseImpl @Inject constructor() : ValidateOtpUseCase {

    override fun validate(otp: String): ValidationResult {
        if (otp.isBlank() || otp.length < 6) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(resId = R.string.invalid_otp),
            )
        }
        return ValidationResult(
            isSuccessful = true,
        )
    }
}
