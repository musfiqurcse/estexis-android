package com.estexis.core.ui.util.validation

import com.estexis.core.ui.util.UiText

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: UiText? = null,
)
