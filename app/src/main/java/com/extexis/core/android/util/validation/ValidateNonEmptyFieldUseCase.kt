package com.extexis.core.android.util.validation

import com.extexis.core.android.util.UiText
import javax.inject.Inject

interface ValidateNonEmptyFieldUseCase {

    fun isEmpty(value: String, errorMessageId: Int): ValidationResult

}

class ValidateNonEmptyFieldUseCaseImpl @Inject constructor() : ValidateNonEmptyFieldUseCase {

    override fun isEmpty(
        value: String,
        errorMessageId: Int
    ): ValidationResult {
        if(value.isEmpty() || value.isBlank()) {
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
