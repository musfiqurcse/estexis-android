package com.estexis.core.ui.util.validation

import androidx.annotation.StringRes
import com.estexis.core.ui.util.UiText
import javax.inject.Inject

interface ValidateNonEmptyFieldUseCase {

    fun isEmpty(value: String, @StringRes errorMessageId: Int): ValidationResult
}

class ValidateNonEmptyFieldUseCaseImpl @Inject constructor() : ValidateNonEmptyFieldUseCase {

    override fun isEmpty(
        value: String,
        errorMessageId: Int
    ): ValidationResult {
        if (value.isEmpty() || value.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(resId = errorMessageId),
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}
