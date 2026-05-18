package com.estexis.core.ui.util.validation

import com.estexis.core.ui.util.UiText
import com.estexis.core.ui.R
import javax.inject.Inject

interface ValidateFullNameUseCase {

    fun validate(fullName: String): ValidationResult
}

class ValidateFullNameUseCaseImpl @Inject constructor() : ValidateFullNameUseCase {

    override fun validate(fullName: String): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(resId = R.string.full_name_is_empty),
            )
        }
        return ValidationResult(
            isSuccessful = true,
        )
    }
}
