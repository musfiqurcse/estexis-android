package com.extexis.core.ui.util.validation

import com.extexis.core.ui.R
import com.extexis.core.ui.util.UiText
import javax.inject.Inject

interface ValidatePasswordUseCase {

    fun validatePassword(password: String, errorMessageId: Int = R.string.password_is_invalid): ValidationResult

    fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult
}

class ValidatePasswordUseCaseImpl @Inject constructor() : ValidatePasswordUseCase {

    override fun validatePassword(password: String, errorMessageId: Int): ValidationResult {
        if (password.isBlank() || password.length < 8) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(resId = errorMessageId),
            )
        }

        val hasLowercase = password.any { it.isLowerCase() }
        val hasUppercase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecial = password.any { !it.isLetterOrDigit() }

        if (!hasLowercase || !hasUppercase || !hasDigit || !hasSpecial) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(resId = errorMessageId)
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }

    override fun validateConfirmPassword(
        password: String,
        confirmPassword: String,
    ): ValidationResult {
        if (confirmPassword.isBlank() || confirmPassword != password) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(resId = R.string.password_does_not_match),
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}
