package com.extexis.core.ui.util.validation

import com.extexis.core.ui.util.UiText

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: UiText? = null,
)
