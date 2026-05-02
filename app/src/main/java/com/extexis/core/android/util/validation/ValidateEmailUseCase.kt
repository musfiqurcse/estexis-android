package com.extexis.core.android.util.validation

import android.util.Patterns
import com.extexis.core.android.R
import com.extexis.core.android.util.UiText
import javax.inject.Inject

interface ValidateEmailUseCase {

    fun validate(input: String): ValidationResult
}

class ValidateEmailUseCaseImpl @Inject constructor() : ValidateEmailUseCase {

    override fun validate(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(resId = R.string.email_is_empty),
            )
        }

        if (!isValidEmail(input)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(resId = R.string.not_a_valid_email),
            )
        }
        return ValidationResult(
            isSuccessful = true,
            errorMessage = null,
        )
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
